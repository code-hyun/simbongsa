package com.app.simbongsa.repository.file;

import com.app.simbongsa.entity.board.ReviewReply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VolunteerWorkFileRepository extends JpaRepository<ReviewReply, Long>, VolunteerWorkFileQueryDsl {
}
