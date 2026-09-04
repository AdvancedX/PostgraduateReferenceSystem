package com.pgs.tool.score;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pgs.agent.AgentUserContext;
import com.pgs.score.domain.YearScore;
import com.pgs.score.service.IYearScoreService;
import com.pgs.security.AgentPermissionGuard;
import com.pgs.tool.AgentToolArgumentException;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ScoreQueryToolTest {
    @Test
    void mapsRangeArgumentsAndCallsExistingService() throws Exception {
        IYearScoreService service = mock(IYearScoreService.class);
        AgentPermissionGuard guard = spy(new AgentPermissionGuard());
        when(service.selectYearScoreList(org.mockito.ArgumentMatchers.any(YearScore.class)))
                .thenReturn(Collections.<YearScore>emptyList());
        AgentUserContext context = context();
        ScoreQueryTool tool = new ScoreQueryTool(service, guard);

        tool.execute(
                context,
                new ObjectMapper().readTree(
                        "{\"school\":\"测试大学\",\"major\":\"计算机\",\"startYear\":2022,\"endYear\":2025,\"limit\":10}"));

        ArgumentCaptor<YearScore> criteria = ArgumentCaptor.forClass(YearScore.class);
        verify(service).selectYearScoreList(criteria.capture());
        verify(guard).require(context, ScoreQueryTool.PERMISSION);
        assertEquals("测试大学", criteria.getValue().getSchool());
        assertEquals("计算机", criteria.getValue().getMajor());
        assertEquals(2022, criteria.getValue().getParams().get("startYear"));
        assertEquals(2025, criteria.getValue().getParams().get("endYear"));
        assertEquals(10, criteria.getValue().getParams().get("limit"));
    }

    @Test
    void rejectsReversedYearRange() throws Exception {
        ScoreQueryTool tool = new ScoreQueryTool(mock(IYearScoreService.class), new AgentPermissionGuard());
        assertThrows(
                AgentToolArgumentException.class,
                () -> tool.execute(
                        context(),
                        new ObjectMapper().readTree("{\"startYear\":2025,\"endYear\":2022}")));
    }

    private AgentUserContext context() {
        return new AgentUserContext(
                2L,
                "tester",
                Collections.<String>emptySet(),
                Collections.singleton(ScoreQueryTool.PERMISSION));
    }
}
