package com.pgs.tool.score;

import com.fasterxml.jackson.databind.JsonNode;
import com.pgs.score.domain.YearScore;
import com.pgs.score.service.IYearScoreService;
import com.pgs.security.AgentPermissionGuard;
import com.pgs.tool.AbstractAgentTool;
import com.pgs.tool.AgentToolArgumentException;
import com.pgs.tool.ToolExecutionResult;
import com.pgs.tool.ToolSchemas;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class ScoreQueryTool extends AbstractAgentTool {
    public static final String PERMISSION = "score:score:list";

    private final IYearScoreService yearScoreService;

    public ScoreQueryTool(IYearScoreService yearScoreService, AgentPermissionGuard permissionGuard) {
        super(permissionGuard);
        this.yearScoreService = yearScoreService;
    }

    @Override
    public String name() {
        return "query_score";
    }

    @Override
    public String description() {
        return "查询系统数据库中的历年专业分数，可按院校、专业、专业代码和年份范围筛选。不得自行计算或编造分数。";
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
                "majorId", ToolSchemas.stringProperty("专业代码"),
                "startYear", ToolSchemas.integerProperty("起始年份，包含该年", 1900, 2100),
                "endYear", ToolSchemas.integerProperty("结束年份，包含该年", 1900, 2100),
                "limit", ToolSchemas.integerProperty("最多返回多少条，默认 20", 1, 100)));
    }

    @Override
    protected ToolExecutionResult doExecute(JsonNode arguments) {
        Integer startYear = optionalYear(arguments, "startYear");
        Integer endYear = optionalYear(arguments, "endYear");
        if (startYear != null && endYear != null && startYear.intValue() > endYear.intValue()) {
            throw new AgentToolArgumentException("startYear 不能晚于 endYear");
        }

        YearScore criteria = new YearScore();
        criteria.setSchool(optionalText(arguments, "school"));
        criteria.setMajor(optionalText(arguments, "major"));
        criteria.setMajorId(optionalText(arguments, "majorId"));
        if (startYear != null) {
            criteria.getParams().put("startYear", startYear);
        }
        if (endYear != null) {
            criteria.getParams().put("endYear", endYear);
        }
        criteria.getParams().put("limit", limit(arguments));

        List<YearScore> matches = yearScoreService.selectYearScoreList(criteria);
        List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
        if (matches != null) {
            for (YearScore score : matches) {
                Map<String, Object> row = new LinkedHashMap<String, Object>();
                row.put("year", score.getYear());
                row.put("school", score.getSchool());
                row.put("majorId", score.getMajorId());
                row.put("major", score.getMajor());
                row.put("score", score.getScore());
                rows.add(row);
            }
        }

        Map<String, Object> data = new LinkedHashMap<String, Object>();
        data.put("count", rows.size());
        data.put("scores", rows);
        return ToolExecutionResult.success(data);
    }

    private Integer optionalYear(JsonNode arguments, String fieldName) {
        JsonNode value = arguments.get(fieldName);
        if (value == null || value.isNull()) {
            return null;
        }
        if (!value.canConvertToInt()) {
            throw new AgentToolArgumentException(fieldName + " 必须是整数");
        }
        int year = value.intValue();
        if (year < 1900 || year > 2100) {
            throw new AgentToolArgumentException(fieldName + " 必须在 1900 到 2100 之间");
        }
        return Integer.valueOf(year);
    }
}
