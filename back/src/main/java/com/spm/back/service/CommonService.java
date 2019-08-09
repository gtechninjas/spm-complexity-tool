package com.spm.back.service;
import java.util.List;

public interface CommonService<T, U> {
	public T add(U u);

	public List<T> getAll();

	public T getById(Long id);

	public boolean deleteById(Long id);
}
