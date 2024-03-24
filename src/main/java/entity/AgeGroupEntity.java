package entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "agegroup", schema = "canadacensusdb", catalog = "")
public class AgeGroupEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ageGroupID", nullable = false)
    private int ageGroupID;
    @Basic
    @Column(name = "description", nullable = false, length = 40)
    private String description;
    
	public int getAgeGroupID() {
		return ageGroupID;
	}
	public void setAgeGroupID(int ageGroupID) {
		this.ageGroupID = ageGroupID;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "AgeGroupEntity [ageGroupID=" + ageGroupID + ", description=" + description + "]";
	}
    

    
}
