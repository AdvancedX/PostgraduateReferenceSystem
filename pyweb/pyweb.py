import torch
import torch.nn as nn
import numpy as np
import pandas as pd
import json
from flask import Flask, request, jsonify
from flask_cors import CORS  # 导入CORS

from sklearn.preprocessing import MinMaxScaler
import os
import math

# 设置随机种子
torch.manual_seed(15)
np.random.seed(15)

app = Flask(__name__)
CORS(app)  # 启用CORS支持，允许所有域名访问

# 模型定义（与原脚本相同）
class PositionalEncoding(nn.Module):
    def __init__(self, d_model, max_len=5000):
        super().__init__()
        position = torch.arange(max_len).unsqueeze(1)
        div_term = torch.exp(torch.arange(0, d_model, 2) * (-math.log(10000.0) / d_model))
        pe = torch.zeros(max_len, d_model)
        pe[:, 0::2] = torch.sin(position * div_term)
        pe[:, 1::2] = torch.cos(position * div_term)
        self.register_buffer('pe', pe.unsqueeze(0))

    def forward(self, x):
        return x + self.pe[:, :x.size(1)]

class EnhancedTransformer(nn.Module):
    def __init__(self, input_dim, seq_len, num_heads=4, num_layers=4, dropout=0.1):
        super().__init__()
        self.embedding = nn.Linear(input_dim, 64)
        self.pos_encoder = PositionalEncoding(64, seq_len)
        self.encoder = nn.TransformerEncoder(
            encoder_layer=nn.TransformerEncoderLayer(
                d_model=64, nhead=num_heads, dim_feedforward=256,
                dropout=dropout, batch_first=True
            ),
            num_layers=num_layers
        )
        self.decoder = nn.Sequential(
            nn.Linear(64, 128), nn.ReLU(), nn.Dropout(dropout),
            nn.Linear(128, 64), nn.ReLU(), nn.Linear(64, 1)
        )

    def forward(self, x):
        x = self.embedding(x)
        x = self.pos_encoder(x)
        x = self.encoder(x)
        return self.decoder(x[:, -1]).squeeze()

def load_model(model_path):
    try:
        checkpoint = torch.load(model_path, map_location=torch.device('cpu'))
        model = EnhancedTransformer(
            input_dim=checkpoint['input_dim'],
            seq_len=checkpoint['seq_len'],
            num_heads=4,
            num_layers=4,
            dropout=0.2
        )
        model.load_state_dict(checkpoint['model_state'])
        model.eval()
        scaler_X = checkpoint['scaler_X']
        scaler_y = checkpoint['scaler_y']
        features = checkpoint['features']
        print("模型加载成功！")
        return model, scaler_X, scaler_y, features
    except Exception as e:
        print(f"加载模型时出错: {e}")
        return None, None, None, None

def predict(model, scaler_X, scaler_y, input_data):
    input_data = np.array(input_data)
    samples, seq_len, features = input_data.shape
    input_reshaped = input_data.reshape(-1, features)
    input_normalized = scaler_X.transform(input_reshaped).reshape(samples, seq_len, features)
    input_tensor = torch.tensor(input_normalized, dtype=torch.float32)
    with torch.no_grad():
        prediction = model(input_tensor).numpy()
    prediction = scaler_y.inverse_transform(prediction.reshape(-1, 1))
    return prediction[0][0].tolist()

# 全局变量
model = None
scaler_X = None
scaler_y = None
features = None
seq_len = 0

# 初始化模型（直接调用，不使用装饰器）
def initialize():
    global model, scaler_X, scaler_y, features, seq_len
    model_path = 'final_transformer_model.pth'
    model, scaler_X, scaler_y, features = load_model(model_path)
    if model is None:
        raise RuntimeError("无法加载模型")
    seq_len = model.pos_encoder.pe.shape[1]
    print(f"模型已初始化，序列长度: {seq_len}")

# 直接初始化
initialize()

@app.route('/predict', methods=['POST'])
def make_prediction():
    try:
        data = request.json
        input_data = data.get('input_data')
        if not input_data:
            return jsonify({'error': '缺少输入数据'}), 400
        if len(input_data) != seq_len:
            return jsonify({'error': f'输入数据长度应为{seq_len}个时间步'}), 400
        for step in input_data:
            if len(step) != len(features):
                return jsonify({'error': f'每个时间步应包含{len(features)}个特征'}), 400
        prediction = predict(model, scaler_X, scaler_y, [input_data])
        return jsonify({
            'prediction': prediction,
            'features': features,
            'seq_len': seq_len
        })
    except Exception as e:
        return jsonify({'error': str(e)}), 500

@app.route('/metadata', methods=['GET'])
def get_metadata():
    return jsonify({
        'features': features,
        'seq_len': seq_len
    })

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)