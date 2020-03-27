package wllt.dto.entityMappers;

import wllt.dto.TransactionDTO;
import wllt.entities.Transaction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Entity Mapper class for {@link Transaction} & {@link TransactionDTO} objects.
 * The class maps an object that has been stated above, to its counterpart.
 *
 * @author Mara Corina
 */
public class TransactionDTOEntityMapper {

    private TransactionDTOEntityMapper(){

    }

    public static Transaction getTransactionFromTransactionDTO(TransactionDTO transactionDTO){
        Transaction transaction = new Transaction();
        if (transactionDTO != null) {
            transaction.setID(transactionDTO.getID());
            transaction.setUser(UserDTOEntityMapper.getUserFromUserDTO(transactionDTO.getUser()));
            transaction.setCategory(CategoryDTOEntityMapper.getCategoryFromDTO(transactionDTO.getCategory()));
            transaction.setSum(transactionDTO.getSum());
            transaction.setDate(transactionDTO.getDate());
        }
        return transaction;
    }

    public static TransactionDTO getDTOFromTransaction(Transaction transaction){
        TransactionDTO transactionDTO = new TransactionDTO();
        if (transaction != null) {
            transactionDTO.setID(transaction.getID());
            transactionDTO.setUser(UserDTOEntityMapper.getDTOFromUser(transaction.getUser()));
            transactionDTO.setCategory(CategoryDTOEntityMapper.getDTOFromCategory(transaction.getCategory()));
            transactionDTO.setSum(transaction.getSum());
            transactionDTO.setDate(transaction.getDate());
        }
        return transactionDTO;
    }

    public static List<TransactionDTO> getTransactionDTOListFromTransactionList(List<Transaction> transactions){

        List<TransactionDTO> transactionDTOList = new ArrayList<>();

        for(Transaction u : transactions){

            transactionDTOList.add(getDTOFromTransaction(u));
        }

        return transactionDTOList;
    }


    public static Set<Transaction> getTransactionListFromDTOList(Set<TransactionDTO> transactionDTOList) {
        Set<Transaction> transactionList = new HashSet<>();
        for (TransactionDTO dto : transactionDTOList) {
            transactionList.add(getTransactionFromTransactionDTO(dto));
        }

        return transactionList;
    }

}