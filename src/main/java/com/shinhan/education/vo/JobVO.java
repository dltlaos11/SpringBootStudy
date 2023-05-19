package com.shinhan.education.vo;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity // JPA
@Table(name="t_jobs")
public class JobVO {
	@Id // key(not null+unique)
	private String jobId;
	@Column(unique = true, nullable = false, length = 35)
	private String jobTitle;
	private int minSalary;
	private int maxSalary;
	@CreationTimestamp // 입력시(insert into..)
	private Timestamp regDate;
	@UpdateTimestamp // 입력 + 수정시(insert into & update ..)
	private Timestamp updateDate;
}
