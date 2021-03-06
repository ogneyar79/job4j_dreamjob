package store;

import model.Candidate;

public class PsqlMain {

    public static void main(String[] args) {
       IStore store = PsqlStore.instOf();
//        store.save(new Post(0, "Java Job"));
//        for (Post post : store.findAllPosts()) {
//            System.out.println(post.getId() + " " + post.getName());
//        }

        store = PsqlStore.instOf();
        store.save(new Candidate(0, "Maksimus", 0, 0));
        for (Candidate candidate : store.findAllCandidates()) {
            System.out.println(candidate.getId() + " " + candidate.getName() + " " + candidate.getPhotoId());
        }
    }
}
