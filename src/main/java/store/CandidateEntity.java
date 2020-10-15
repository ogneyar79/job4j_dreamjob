package store;

import model.Candidate;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class CandidateEntity implements IPsqlStoreBase<Candidate> {

    final BasicDataSource pool = PsqlStore.instOf().getPool();

    @Override
    public Collection<Candidate> findAllEntity() {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM candidate")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    candidates.add(new Candidate(it.getInt("id"), it.getString("name"), it.getInt("photoId")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return candidates;
    }

    @Override
    public void save(Candidate candidate) {
        if (candidate.getId() == 0) {
            create(candidate);
        } else {
            update(candidate);
        }
    }

    private void update(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(" UPDATE candidate set name = ?, photoId = ? where id = ? ")
        ) {
            ps.setString(1, candidate.getName());
            ps.setInt(2, candidate.getPhotoId());
            ps.setInt(3, candidate.getId());
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private Candidate create(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("INSERT INTO candidate(name) VALUES (?)", PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, candidate.getName());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    candidate = new Candidate(id.getInt(1), candidate.getName(), candidate.getPhotoId());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return candidate;
    }

    @Override
    public Candidate findById(int id) {
        return this.findAllEntity().stream().filter(c -> c.getId() == (id)).
                findAny().orElse(new Candidate(id, "Zero", 0));
    }
}
