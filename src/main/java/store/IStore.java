package store;

import model.Candidate;
import model.Post;

import java.util.Collection;

public interface IStore {

    Collection<Post> findAllPosts();

    Collection<Candidate> findAllCandidates();

    void save(Post post);

    Post findById(int id);

}
