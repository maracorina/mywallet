package wllt.manager.remote;

import wllt.dto.TransactionDTO;
import wllt.exceptions.BusinessException;
import wllt.exceptions.ValidationException;

import java.util.List;
import java.util.Set;

/**
 * Interface for Remote usage
 *
 * @author Mara Corina
 */
public interface TransactionsManager {

    TransactionDTO insertTransaction(TransactionDTO transactionDTO) throws BusinessException, ValidationException;

    TransactionDTO findTransaction(Integer id) throws BusinessException;

    List<TransactionDTO> getUserTransactions(Integer userId) throws BusinessException;

    List<TransactionDTO> getUserCategoryTransactions(Integer userId, Integer categoryId) throws BusinessException;
}
