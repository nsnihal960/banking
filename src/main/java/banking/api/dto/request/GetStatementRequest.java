package banking.api.dto.request;

import banking.api.dto.response.exception.ClientException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class GetStatementRequest implements Request {
    public final Long id;
    public final Long startTime;
    public final Long endTime;
    public final Integer count;

    @JsonCreator
    public GetStatementRequest(@JsonProperty("startTime") Long startTime,
                               @JsonProperty("endTime") Long endTime,
                               @JsonProperty("count") Integer count,
                               @JsonProperty("id") Long id) {
        this.startTime = startTime == null ? 0L : startTime;
        this.endTime = endTime == null ? Long.MAX_VALUE : endTime;
        this.count = count == null ? 10 : count;
        this.id = id;
    }

    @Override
    public String toString() {
        return "GetStatementRequest{" +
                "id=" + id +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", count=" + count +
                '}';
    }

    @Override
    public void validate() {
        List<String> errors = new ArrayList<>();
        if (id == null || id <= 0) {
            errors.add("User Id cannot be blank");
        }
        if (errors.size() > 0) {
            throw new ClientException(errors.toString());
        }
    }
}
