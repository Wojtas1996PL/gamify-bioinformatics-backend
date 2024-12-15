package project.bioinformatics.mapper;

import org.mapstruct.Mapper;
import project.bioinformatics.config.MapperConfig;
import project.bioinformatics.dto.UserExerciseDto;
import project.bioinformatics.model.UserExercise;

@Mapper(config = MapperConfig.class, componentModel = "spring")
public interface UserExerciseMapper {

    UserExerciseDto toUserExerciseDto(UserExercise userExercise);
}
