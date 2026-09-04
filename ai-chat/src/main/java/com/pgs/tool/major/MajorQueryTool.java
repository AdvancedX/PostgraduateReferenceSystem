package com.pgs.tool.major;

import com.fasterxml.jackson.databind.JsonNode;
import com.pgs.major.domain.MajorInfo;
import com.pgs.major.service.IMajorInfoService;
import com.pgs.security.AgentPermissionGuard;
import com.pgs.tool.AbstractAgentTool;
import com.pgs.tool.ToolExecutionResult;
import com.pgs.tool.ToolSchemas;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class MajorQueryTool extends AbstractAgentTool {
    public static final String PERMISSION = "major:majorinfo:list";

    private final IMajorInfoService majorInfoService;

    public MajorQueryTool(IMajorInfoService majorInfoService, AgentPermissionGuard permissionGuard) {
        super(permissionGuard);
        this.majorInfoService = majorInfoService;
    }

    @Override
    public String name() {
        return "query_major";
    }

    @Override
    public String description() {
        return "查询系统数据库中的招生专业、研究方向和初试科目，可按院校、专业或研究方向筛选。";
    }

    @Override
    public String requiredPermission() {
        return PERMISSION;
    }

    @Override
    public Map<String, Object> inputSchema() {
        return ToolSchemas.objectSchema(ToolSchemas.properties(
                "school", ToolSchemas.stringProperty("院校名称或名称关键词"),
                "major", ToolSchemas.stringProperty("专业名称或名称关键词"),
                "researchDirection", ToolSchemas.stringProperty("研究方向关键词"),
                "limit", ToolSchemas.integerProperty("最多返回多少条，默认 20", 1, 100)));
    }

    @Override
    protected ToolExecutionResult doExecute(JsonNode arguments) {
        MajorInfo criteria = new MajorInfo();
        criteria.setSchool(optionalText(arguments, "school"));
        criteria.setMajor(optionalText(arguments, "major"));
        criteria.setResearchDirection(optionalText(arguments, "researchDirection"));
        criteria.getParams().put("limit", limit(arguments));

        List<MajorInfo> matches = majorInfoService.selectMajorInfoList(criteria);
        List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
        if (matches != null) {
            for (MajorInfo major : matches) {
                Map<String, Object> row = new LinkedHashMap<String, Object>();
                row.put("id", major.getId());
                row.put("school", major.getSchool());
                row.put("major", major.getMajor());
                row.put("researchDirection", major.getResearchDirection());
                row.put("politics", major.getPolitics());
                row.put("language", major.getLanguage());
                row.put("majorSubject1", major.getMajorsubject1());
                row.put("majorSubject2", major.getMajorSubject2());
                rows.add(row);
            }
        }

        Map<String, Object> data = new LinkedHashMap<String, Object>();
        data.put("count", rows.size());
        data.put("majors", rows);
        return ToolExecutionResult.success(data);
    }
}
