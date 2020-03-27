package wllt.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wllt.dao.CategoryDao;
import wllt.dao.TransactionDao;
import wllt.dao.UserCategoryDao;
import wllt.dao.UserDao;
import wllt.dto.TransactionDTO;
import wllt.dto.UserDTO;
import wllt.dto.entityMappers.TransactionDTOEntityMapper;
import wllt.entities.Category;
import wllt.entities.Transaction;
import wllt.entities.User;
import wllt.entities.utils.UserCategoryId;
import wllt.exceptions.BusinessException;
import wllt.exceptions.ValidationException;
import wllt.manager.remote.TransactionsManager;
import wllt.validators.TransactionValidator;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@Component
public class TransactionsManagerImpl implements TransactionsManager {

    @Autowired
    private TransactionDao transactionDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private UserCategoryDao userCategoryDao;

    private Logger logger = Logger.getLogger(TransactionsManagerImpl.class.getName());

    /**
     * @param transactionDTO is an {@link TransactionDTO} object that maps the {@link Transaction}
     *                object that will be persisted in the database.
     * @throws {@link BusinessException} if the {@link TransactionDTO} object is
     *                invalid
     * @author Mara Corina
     */
    @Override
    public TransactionDTO insertTransaction(TransactionDTO transactionDTO) throws BusinessException, ValidationException {
        if (this.userDao.findAllByID(transactionDTO.getUser().getID()) == null) {
            throw new BusinessException("TransactionBusinessException01", "No user with this id was found!");
        }

        if(this.categoryDao.findAllByID(transactionDTO.getCategory().getID()) == null){
            throw new BusinessException("TransactionBusinessException02", "No category with this id was found");
        }

        if(this.userCategoryDao.findAllByID(new UserCategoryId(transactionDTO.getUser().getID(),
                transactionDTO.getCategory().getID())) == null) {
            throw new BusinessException("TransactionBusinessException03", "No category with this id was assigned to this user");
        }
        TransactionValidator.validate(transactionDTO);
        Transaction persistedTransaction = this.transactionDao.save(TransactionDTOEntityMapper.getTransactionFromTransactionDTO(transactionDTO));
        persistedTransaction = this.transactionDao.findAllByID(persistedTransaction.getID());
        return TransactionDTOEntityMapper.getDTOFromTransaction(persistedTransaction);
    }

    @Override
    public TransactionDTO findTransaction(Integer id) throws BusinessException {
        Transaction transaction = this.transactionDao.findAllByID(id);
        if (transaction == null) {
            throw new BusinessException("Find transaction error: ", "No transaction with this id was found!");
        }
        return TransactionDTOEntityMapper.getDTOFromTransaction(transaction);
    }

    @Override
    public List<TransactionDTO> getUserTransactions(Integer userId) throws BusinessException {
        if (this.userDao.findAllByID(userId) == null) {
            throw new BusinessException("TransactionBusinessException04", "No user with this id was found!");
        }
        List<Transaction> transactions = this.transactionDao.findByUserID(userId);
        return TransactionDTOEntityMapper.getTransactionDTOListFromTransactionList(transactions);
    }

    @Override
    public List<TransactionDTO> getUserCategoryTransactions(Integer userId, Integer categoryId) throws BusinessException {
        if (this.userDao.findAllByID(userId) == null) {
            throw new BusinessException("TransactionBusinessException05", "No user with this id was found!");
        }

        if(this.categoryDao.findAllByID(categoryId) == null){
            throw new BusinessException("TransactionBusinessException06", "No category with this id was found");
        }
        List<Transaction> transactions = this.transactionDao.findByUserIDAndCategoryID(userId, categoryId);
        return TransactionDTOEntityMapper.getTransactionDTOListFromTransactionList(transactions);
    }
}
