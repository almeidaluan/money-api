package com.apimoney.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.apimoney.models.Lancamento;
import com.apimoney.vo.LancamentoVO;

public interface LancamentoRepositoryQuery {
	public Page<Lancamento> filtraPorDataDescricao(LancamentoVO lancamentoVO, Pageable pageable);

}
