package br.com.ibpt.mappers.v1;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.ibpt.data.vo.v1.VersionVO;
import br.com.ibpt.model.v1.Version;

@Mapper(componentModel = "spring")
public interface VersionMapper {

	@Mapping(source = "id", target = "key")
	VersionVO toVersionVO(Version entity);
	
	@Mapping(source = "key", target = "id")
	Version toVersion(VersionVO vo);
	
	@Mapping(source = "id", target = "key")
	List<VersionVO> toVersionVOList(List<Version> entityList);
	
	@Mapping(source = "key", target = "id")
	List<Version> toVersionList(List<VersionVO> voList);
}
