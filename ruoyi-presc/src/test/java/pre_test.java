import com.ruoyi.presc.domain.PredictSc;
import org.apache.commons.math3.analysis.ParametricUnivariateFunction;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresBuilder;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresOptimizer;
import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;
import org.apache.commons.math3.fitting.leastsquares.MultivariateJacobianFunction;
import org.apache.commons.math3.linear.DecompositionSolver;
import org.apache.commons.math3.linear.RealVector;
import java.util.Scanner;
import org.junit.Test;


public class pre_test {
    @Test
    public void test() {
        double[] years = {1, 2, 3, 4, 5}; // 前五年的年份
        double[] scores = {80, 85, 90, 92, 95}; // 前五年的分数
        double yearToPredict = 6; // 第六年的年份
        double sumX = 0.0;
        double sumY = 0.0;
        double sumXY = 0.0;
        double sumX2 = 0.0;

        for (int i = 0; i < years.length; i++) {
            sumX += years[i];
            sumY += scores[i];
            sumXY += years[i] * scores[i];
            sumX2 += years[i] * years[i];
        }

        double n = years.length;
        double slope = (n * sumXY - sumX * sumY) / (n * sumX2 - sumX * sumX);
        double intercept = (sumY - slope * sumX) / n;
        double predictedScore = intercept + slope * yearToPredict;
        // 限制预测值与前一年的分数差值不超过10
        if (years.length > 0) {
            double lastYearScore = scores[years.length - 1];
            double diff = Math.abs(predictedScore - lastYearScore);
            if (diff > 10) {
                if (predictedScore > lastYearScore) {
                    predictedScore = lastYearScore + 10;
                } else {
                    predictedScore = lastYearScore - 10;
                }
            }
        }
        System.out.println("预测的第六年的分数为: " + predictedScore);
    }


    @Test
    public void test2(){double[] years = {1, 2, 3, 4, 5}; // 前五年的年份
        double[] scores = {80, 85, 90, 92, 95}; // 前五年的分数
        double yearToPredict = 6; // 第六年的年份
        int polynomialDegree = 2; // 多项式的阶数
        double predictedScore = polynomialRegression(years, scores, yearToPredict, polynomialDegree);
        System.out.println("预测的第六年的分数为: " + predictedScore);
    }
    // 多项式回归
    public static double polynomialRegression(double[] years, double[] scores, double yearToPredict, int polynomialDegree) {
        if (years.length != scores.length || years.length < 2) {
            throw new IllegalArgumentException("年份和分数的数量必须相等且大于等于2");
        }

        WeightedObservedPoints points = new WeightedObservedPoints();
        for (int i = 0; i < years.length; i++) {
            points.add(years[i], scores[i]);
        }

        PolynomialCurveFitter fitter = PolynomialCurveFitter.create(polynomialDegree);
        double[] coefficients = fitter.fit(points.toList());

        PolynomialFunction polynomialFunction = new PolynomialFunction(coefficients);
        double predictedScore = polynomialFunction.value(yearToPredict);

        return predictedScore;
    }
}

