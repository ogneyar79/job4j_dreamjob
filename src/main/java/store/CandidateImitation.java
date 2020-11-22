package store;

import model.Candidate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CandidateImitation implements IPsqlStoreBase<Candidate> {

    private final Map<Integer, Candidate> store = new HashMap();
    private int ids = 1;

    private Candidate add(Candidate candidate) {
        Candidate candidateR = new Candidate(ids, candidate.getName(), candidate.getPhotoId(), candidate.getCityId());
        ids++;
        this.store.put(candidateR.getId(), candidateR);
        return candidate;
    }

    @Override
    public Collection<Candidate> findAllEntity() {
        return new ArrayList(this.store.values());
    }

    @Override
    public void save(Candidate candidate) {
        if (candidate.getId() == 0) {
            add(candidate);
        } else {
            this.store.put(candidate.getId(), candidate);
        }
    }

    @Override
    public Candidate findById(int id) {
        return store.get(id) != null ? store.get(id) : new Candidate(0, "Zero", 000, 000);
    }
}
