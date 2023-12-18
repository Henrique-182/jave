package br.com.conhecimento.data.vo.v1;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;

public class TopicVO extends RepresentationModel<TopicVO> implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer key;
	private String name;
	
	public TopicVO() {}

	public Integer getKey() {
		return key;
	}

	public void setKey(Integer key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(key, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TopicVO other = (TopicVO) obj;
		return Objects.equals(key, other.key) && Objects.equals(name, other.name);
	}

}
