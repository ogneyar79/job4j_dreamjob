package store;

import model.Candidate;
import model.Photo;
import model.Post;

import java.util.Collection;

public interface IStore {

    Collection<Post> findAllPosts();

    Collection<Candidate> findAllCandidates();

    void save(Post post);

    Post findById(int id);

    void save(Candidate candidate);

    void save(Photo photo);

    Collection<Photo> findAllPhoto();

    Photo findPhotoCandidates(Candidate model);


}
