package wllt.utils;

import wllt.entities.types.StatusType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Optional;

@Converter(autoApply = true)
public class StatusConverter implements AttributeConverter<StatusType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(StatusType statusType) {
        return Optional.ofNullable(statusType.getActualValue())
                .orElse(null);
    }

    @Override
    public StatusType convertToEntityAttribute(Integer status) {
        return Optional.ofNullable(StatusType.get(status))
                .orElse(null);
    }
}
