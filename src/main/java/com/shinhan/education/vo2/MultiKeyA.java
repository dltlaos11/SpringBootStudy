package com.shinhan.education.vo2;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MultiKeyA implements Serializable{
	private Integer id1;
	private Integer id2;
}
