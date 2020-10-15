package store;

import model.Candidate;
import model.Photo;
import model.Post;
import org.apache.commons.dbcp2.BasicDataSource;

import java.util.Collection;

public interface IStore {

    BasicDataSource getPool();
    Collection<Post> findAllPosts();

    Collection<Candidate> findAllCandidates();

    void save(Post post);

    Post findById(int id);

    void save(Candidate candidate);

    void save(Photo photo);

    Collection<Photo> findAllPhoto();

    Photo findPhotoCandidates(Candidate model);

    Photo findPhotoByName2(String name);

    Photo findByName(String name);
    public Photo findPhotoById(int id);

    Candidate findCandidate(int id);
}
