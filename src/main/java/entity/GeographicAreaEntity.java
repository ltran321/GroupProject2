package entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "geographicarea", schema = "canadacensusdb", catalog = "")
public class GeographicAreaEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "geographicAreaID", nullable = false)
    private int geographicAreaID;
    @Basic
    @Column(name = "code", nullable = false)
    private int code;
    @Basic
    @Column(name = "level", nullable = false)
    private int level;
    @Basic
    @Column(name = "name", nullable = false, length = 40)
    private String name;
    @Basic
    @Column(name = "alternativeCode", nullable = false)
    private int alternativeCode;
    
	public int getGeographicAreaID() {
		return geographicAreaID;
	}
	public void setGeographicAreaID(int geographicAreaID) {
		this.geographicAreaID = geographicAreaID;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAlternativeCode() {
		return alternativeCode;
	}
	public void setAlternativeCode(int alternativeCode) {
		this.alternativeCode = alternativeCode;
	}
	@Override
	public String toString() {
		return "GeographicAreaEntity [geographicAreaID=" + geographicAreaID + ", code=" + code + ", level=" + level
				+ ", name=" + name + ", alternativeCode=" + alternativeCode + "]";
	}
    
    
}
