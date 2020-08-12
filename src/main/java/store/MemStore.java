package store;

import model.Candidate;
import model.Post;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemStore {

    private static final MemStore INST = new MemStore();
    private static AtomicInteger POST_ID = new AtomicInteger(4);
    private static AtomicInteger CANDIDATE_ID = new AtomicInteger(3);

    private Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();


    private MemStore() {
        posts.put(1, new Post(1, "Junior Java Job"));
        posts.put(2, new Post(2, "Middle Java Job"));
        posts.put(3, new Post(3, "Senior Java Job"));
        candidates.put(1, new Candidate(1, "Junior Java"));
        candidates.put(2, new Candidate(2, "Middle Java"));
        candidates.put(3, new Candidate(3, "Senior Java"));
    }

    public void save(Post post) {
        if (post.getId() == 0) {
            Post post1 = new Post(POST_ID.incrementAndGet(), post.getName());
            posts.put(post1.getId(), post1);
            return;
        }
        posts.put(post.getId(), post);
    }

    public Post findById(int id) {
        return posts.get(id);
    }

    public void save(Candidate candidate) {
        Candidate model = new Candidate(CANDIDATE_ID.incrementAndGet(), candidate.getName());
        candidates.put(model.getId(), model);
    }

    public Collection<Post> findAll() {
        return posts.values();
    }

    public static MemStore instOf() {
        return INST;
    }

    public Collection<Post> findAllPosts() {
        return posts.values();
    }

    public Collection<Candidate> findAllCandidates() {
        return candidates.values();
    }
}
