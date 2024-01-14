package br.com.migrations.model.v1;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(schema = "PUBLIC", name = "FLYWAY_SCHEMA_HISTORY")
public class FlywaySchemaHistory implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "INSTALLED_RANK", nullable = false)
	private Integer installeRank;
	
	@Column(name = "VERSION", nullable = false)
	private String version;
	
	@Column(name = "DESCRIPTION", nullable = false)
	private String description;
	
	@Column(name = "TYPE", nullable = false)
	private String type;
	
	@Column(name = "SCRIPT", nullable = false)
	private String script;
	
	@Column(name = "CHECKSUM", nullable = false)
	private Integer checksum;
	
	@Column(name = "INSTALLED_BY", nullable = false)
	private String installedBy;
	
	@Column(name = "INSTALLED_ON", nullable = false)
	private Date installedOn;
	
	@Column(name = "EXECUTION_TIME", nullable = false)
	private Integer executionTime;
	
	@Column(name = "SUCCESS", nullable = false)
	private Boolean Success;

	public FlywaySchemaHistory() {}

	public Integer getInstalleRank() {
		return installeRank;
	}

	public void setInstalleRank(Integer installeRank) {
		this.installeRank = installeRank;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public Integer getChecksum() {
		return checksum;
	}

	public void setChecksum(Integer checksum) {
		this.checksum = checksum;
	}

	public String getInstalledBy() {
		return installedBy;
	}

	public void setInstalledBy(String installedBy) {
		this.installedBy = installedBy;
	}

	public Date getInstalledOn() {
		return installedOn;
	}

	public void setInstalledOn(Date installedOn) {
		this.installedOn = installedOn;
	}

	public Integer getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(Integer executionTime) {
		this.executionTime = executionTime;
	}

	public Boolean getSuccess() {
		return Success;
	}

	public void setSuccess(Boolean success) {
		Success = success;
	}

	@Override
	public int hashCode() {
		return Objects.hash(Success, checksum, description, executionTime, installeRank, installedBy, installedOn,
				script, type, version);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FlywaySchemaHistory other = (FlywaySchemaHistory) obj;
		return Objects.equals(Success, other.Success) && Objects.equals(checksum, other.checksum)
				&& Objects.equals(description, other.description) && Objects.equals(executionTime, other.executionTime)
				&& Objects.equals(installeRank, other.installeRank) && Objects.equals(installedBy, other.installedBy)
				&& Objects.equals(installedOn, other.installedOn) && Objects.equals(script, other.script)
				&& Objects.equals(type, other.type) && Objects.equals(version, other.version);
	}
	
}
