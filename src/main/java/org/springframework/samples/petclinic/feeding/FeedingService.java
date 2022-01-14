package org.springframework.samples.petclinic.feeding;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.pet.exceptions.DuplicatedPetNameException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FeedingService {
	
	@Autowired
	private FeedingRepository feedingRepository;
	
    public List<Feeding> getAll(){
    	List<Feeding> f = feedingRepository.findAll();
        return f;
    }

    public List<FeedingType> getAllFeedingTypes(){
    	List<FeedingType> f = feedingRepository.findAllFeedingTypes();
        return f;
    }

    public FeedingType getFeedingType(String typeName) {
    	FeedingType fT = feedingRepository.findFeedingType(typeName);
        return fT;
    }

    
    @Transactional(rollbackFor = UnfeasibleFeedingException.class)
    public Feeding save(Feeding p) throws UnfeasibleFeedingException {
    	Optional<Feeding>  f = feedingRepository.findById(p.getId());
    	if(p.getPet().getType()!= f.get().getPet().getType()) {
    		throw new UnfeasibleFeedingException();
    	}else {
    		Feeding res = feedingRepository.save(p);
    		return res; 
    	}
              
    }

    
}
