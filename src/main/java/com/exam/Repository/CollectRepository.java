package com.exam.Repository;


import com.exam.model.entity.Collect;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Created by mac on 2017/2/28.
 */
public interface CollectRepository extends JpaRepository<Collect,Integer> {
    public List<Collect> findByControlPointId(int controlPointId);
    public Collect findByCollectionId(int collectionId);
}
