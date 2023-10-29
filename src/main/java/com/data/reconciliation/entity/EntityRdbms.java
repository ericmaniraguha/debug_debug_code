package com.data.reconciliation.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "messsage")
public class EntityRdbms {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recid;

    @Column
    private Long id;

    @Column
    private String msg;

    @Column
    private LocalDateTime produced_at;
    
    // Getters 

	public void setRecid(Long recid) {
		this.recid = recid;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setProduced_at(LocalDateTime produced_at) {
		this.produced_at = produced_at;
	}

	public Long getRecid() {
		return recid;
	}

	public Long getId() {
		return id;
	}

	public String getMsg() {
		return msg;
	}

	public LocalDateTime getProduced_at() {
		return produced_at;
	}

	// default constructor
	public EntityRdbms() {
	}
	//constructors 
	
	public EntityRdbms(Long recid, Long id, String msg, LocalDateTime produced_at) {
		this.recid = recid;
		this.id = id;
		this.msg = msg;
		this.produced_at = produced_at;
	}
	
	public EntityRdbms( Long id, String msg, LocalDateTime produced_at) {
		
		this.id = id;
		this.msg = msg;
		this.produced_at = produced_at;
	}

}