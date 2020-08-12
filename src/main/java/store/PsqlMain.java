package store;

import model.Post;

public class PsqlMain {

    public static void main(String[] args) {
        IStore store = PsqlStore.instOf();
        store.save(new Post(0, "Java Job"));
        for (Post post : store.findAllPosts()) {
            System.out.println(post.getId() + " " + post.getName());
        }
    }
}
