package com.data.reconciliation.entity;

import java.time.LocalDateTime;

import org.springframework.data.elasticsearch.annotations.Document;
import jakarta.persistence.Id;

@Document(indexName = "messages")
public class EntityEs {
	
	@Id
	private String id;
	private String msg;
	private LocalDateTime produced_at;
	
	//setter and getters
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public LocalDateTime getProduced_at() {
		return produced_at;
	}
	public void setProduced_at(LocalDateTime produced_at) {
		this.produced_at = produced_at;
	}
	
	// default constructor
	public EntityEs() {
	}
	//constructors 
	
	public EntityEs(String id, String msg, LocalDateTime produced_at) {
		this.id = id;
		this.msg = msg;
		this.produced_at = produced_at;
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", msg=" + msg + ", produced_at=" + produced_at + "]";
	} 
	
}