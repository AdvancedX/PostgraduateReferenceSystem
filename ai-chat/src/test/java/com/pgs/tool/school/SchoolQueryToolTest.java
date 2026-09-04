package com.pgs.tool.school;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pgs.agent.AgentUserContext;
import com.pgs.school.domain.SchoolInfo;
import com.pgs.school.service.ISchoolInfoService;
import com.pgs.security.AgentPermissionGuard;
import com.pgs.tool.ToolExecutionResult;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SchoolQueryToolTest {
    @Test
    void mapsArgumentsCallsServiceAndChecksPermission() throws Exception {
        ISchoolInfoService service = mock(ISchoolInfoService.class);
        AgentPermissionGuard guard = spy(new AgentPermissionGuard());
        SchoolInfo match = new SchoolInfo();
        match.setId("1");
        match.setSchool("测试大学");
        match.setSchooltype("985/211");
        match.setRegion("北京");
        when(service.selectSchoolInfoList(org.mockito.ArgumentMatchers.any(SchoolInfo.class)))
                .thenReturn(Collections.singletonList(match));

        AgentUserContext context = context(SchoolQueryTool.PERMISSION);
        SchoolQueryTool tool = new SchoolQueryTool(service, guard);
        ToolExecutionResult result = tool.execute(
                context,
                new ObjectMapper().readTree(
                        "{\"schoolName\":\"测试\",\"region\":\"北京\",\"schoolType\":\"985\",\"limit\":5}"));

        ArgumentCaptor<SchoolInfo> criteria = ArgumentCaptor.forClass(SchoolInfo.class);
        verify(service).selectSchoolInfoList(criteria.capture());
        verify(guard).require(context, SchoolQueryTool.PERMISSION);
        assertEquals("测试", criteria.getValue().getSchool());
        assertEquals("北京", criteria.getValue().getRegion());
        assertEquals("985", criteria.getValue().getSchooltype());
        assertEquals(5, criteria.getValue().getParams().get("limit"));
        assertEquals(true, result.isSuccess());
    }

    private AgentUserContext context(String permission) {
        return new AgentUserContext(
                2L,
                "tester",
                Collections.<String>emptySet(),
                Collections.singleton(permission));
    }
}
