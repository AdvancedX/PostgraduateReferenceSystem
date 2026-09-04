package com.pgs.tool.school;

import com.fasterxml.jackson.databind.JsonNode;
import com.pgs.school.domain.SchoolInfo;
import com.pgs.school.service.ISchoolInfoService;
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
public class SchoolQueryTool extends AbstractAgentTool {
    public static final String PERMISSION = "school:schoolinfo:list";

    private final ISchoolInfoService schoolInfoService;

    public SchoolQueryTool(ISchoolInfoService schoolInfoService, AgentPermissionGuard permissionGuard) {
        super(permissionGuard);
        this.schoolInfoService = schoolInfoService;
    }

    @Override
    public String name() {
        return "query_school";
    }

    @Override
    public String description() {
        return "查询系统数据库中的研究生招生院校，可按院校名、地区和院校类型筛选。涉及院校事实时应使用此工具，不要凭常识编造。";
    }

    @Override
    public String requiredPermission() {
        return PERMISSION;
    }

    @Override
    public Map<String, Object> inputSchema() {
        return ToolSchemas.objectSchema(ToolSchemas.properties(
                "schoolName", ToolSchemas.stringProperty("院校名称或名称关键词"),
                "region", ToolSchemas.stringProperty("院校所在地区，使用数据库中的省市名称"),
                "schoolType", ToolSchemas.stringProperty("院校类型关键词，例如 985、211、双一流"),
                "limit", ToolSchemas.integerProperty("最多返回多少条，默认 20", 1, 100)));
    }

    @Override
    protected ToolExecutionResult doExecute(JsonNode arguments) {
        String schoolName = optionalText(arguments, "schoolName");
        String region = optionalText(arguments, "region");
        String schoolType = optionalText(arguments, "schoolType");
        int limit = limit(arguments);

        SchoolInfo criteria = new SchoolInfo();
        criteria.setSchool(schoolName);
        criteria.setRegion(region);
        criteria.setSchooltype(schoolType);
        criteria.getParams().put("limit", limit);

        List<SchoolInfo> matches = schoolInfoService.selectSchoolInfoList(criteria);
        List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
        if (matches != null) {
            for (SchoolInfo school : matches) {
                Map<String, Object> row = new LinkedHashMap<String, Object>();
                row.put("id", school.getId());
                row.put("school", school.getSchool());
                row.put("schoolType", school.getSchooltype());
                row.put("region", school.getRegion());
                rows.add(row);
            }
        }

        Map<String, Object> data = new LinkedHashMap<String, Object>();
        data.put("count", rows.size());
        data.put("schools", rows);
        return ToolExecutionResult.success(data);
    }
}
