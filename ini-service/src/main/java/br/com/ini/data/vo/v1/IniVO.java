package br.com.ini.data.vo.v1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class IniVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String cnpj;
	private String filename;
	private List<SectionVO> sections;

	public IniVO() {
		sections = new ArrayList<>();
	}
	
	public Boolean addSectionVO(SectionVO section) {
		return sections.add(section);
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public List<SectionVO> getSections() {
		return sections;
	}

	public void setSections(List<SectionVO> sections) {
		this.sections = sections;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cnpj, filename, sections);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IniVO other = (IniVO) obj;
		return Objects.equals(cnpj, other.cnpj) && Objects.equals(filename, other.filename)
				&& Objects.equals(sections, other.sections);
	}

}
