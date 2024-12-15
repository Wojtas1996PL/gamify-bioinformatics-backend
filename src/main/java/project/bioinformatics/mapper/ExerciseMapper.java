package project.bioinformatics.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import project.bioinformatics.config.MapperConfig;
import project.bioinformatics.dto.ExerciseCreateDto;
import project.bioinformatics.dto.ExerciseDto;
import project.bioinformatics.model.Exercise;

@Mapper(config = MapperConfig.class, componentModel = "spring")
public interface ExerciseMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", constant = "false")
    Exercise toExercise(ExerciseCreateDto exerciseCreateDto);

    ExerciseDto toDto(Exercise exercise);
}
