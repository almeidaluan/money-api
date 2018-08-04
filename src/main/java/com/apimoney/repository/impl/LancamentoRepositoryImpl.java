package com.apimoney.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import com.apimoney.models.Lancamento;
import com.apimoney.models.Lancamento_;
import com.apimoney.repository.LancamentoRepositoryQuery;
import com.apimoney.vo.LancamentoVO;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Lancamento> filtraPorDataDescricao(LancamentoVO lancamentoVO) {

		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Lancamento> criteria = builder.createQuery(Lancamento.class);

		Root<Lancamento> root = criteria.from(Lancamento.class);

		Predicate[] predicates = criarRestricoes(lancamentoVO, builder, root);

		criteria.where(predicates);

		TypedQuery<Lancamento> query = manager.createQuery(criteria);
		return query.getResultList();
	}

	private Predicate[] criarRestricoes(LancamentoVO lancamentoVO, CriteriaBuilder builder, Root<Lancamento> root) {

		List<Predicate> predicates = new ArrayList<>();

		if (!StringUtils.isEmpty(lancamentoVO.getDescricao())) {
			predicates.add(builder.like(builder.lower(root.get(Lancamento_.descricao)),
					"%" + lancamentoVO.getDescricao().toLowerCase() + "%"));
		}

		if (lancamentoVO.getDataVencimentoDe() != null) {
			predicates
					.add(builder.greaterThan(root.get(Lancamento_.dataVencimento), lancamentoVO.getDataVencimentoDe()));
		}

		if (lancamentoVO.getDataVencimentoAte() != null) {
			predicates.add(builder.lessThan(root.get(Lancamento_.dataVencimento), lancamentoVO.getDataVencimentoAte()));
		}

		return predicates.toArray(new Predicate[predicates.size()]);

	}

}
