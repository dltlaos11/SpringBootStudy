package com.shinhan.education.vo;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
//@ToString(exclude = "files2") // 모든 칼럼 가져오는거 방지 서로..files2는 자시에서 X
@ToString
@Entity // JPA가 관리
@Table(name = "tbl_pdsboard")
@EqualsAndHashCode(of = "pid") // pid가 같으면 같은 것, 없으면 같은 칼럼이면 같은 것으로 인식
public class PDSBoard {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private Long pid;
private String pname;
private String pwriter;
    //cascade:영속성전이 PDSBoard변경시 PDSFile변경)
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER) 
    //즉시로딩 insert, inset, update || page는 정해주기
    //JPA는 default로 지연로딩을 사용한다.(PDSBoard조회시 PDSFile 조인하지않음) 
    //1)fetch = FetchType.LAZY ....PDSFile과 연결불가, @Query로 해결 
    //2)fetch = FetchType.EAGER....PDSFile과 연결가능 
    
    @JoinColumn(name="pdsno") //PDSFile칼럼에 생성 & 누구와 join되는지 ! @OneToMany
    // 나의 것(board)을 참조하면 중간 테이블 필요 but joinColumn으로 하면 중간테이블 없이 pdsno를 files 테이블에 칼럼생성하여
    // 참조하도록 한다. 
    // @JoinColumn(name="pdsno")을 주석처리하면 중간에 중간테이블이 하나 생성된다.
//    Hibernate: create table tbl_pdsboard_files2 (pdsboard_pid number(19,0) not null, files2_fno number(19,0) not null)
    private List<PDSFile> files2; // PDSFile 칼럼에 존재 PDSBoard에는 ❌
}
