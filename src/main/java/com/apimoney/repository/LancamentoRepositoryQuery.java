package com.apimoney.repository;

import java.util.List;

import com.apimoney.models.Lancamento;
import com.apimoney.vo.LancamentoVO;

public interface LancamentoRepositoryQuery {

	public List<Lancamento> filtra(LancamentoVO lancamentoVO);
}
