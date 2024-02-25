package br.com.jave.data.vo.v1;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class FileVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<LineVO> lines;

	public FileVO() {}

	public List<LineVO> getLines() {
		return lines;
	}

	public void setLines(List<LineVO> lines) {
		this.lines = lines;
	}

	@Override
	public int hashCode() {
		return Objects.hash(lines);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FileVO other = (FileVO) obj;
		return Objects.equals(lines, other.lines);
	}
	
}
