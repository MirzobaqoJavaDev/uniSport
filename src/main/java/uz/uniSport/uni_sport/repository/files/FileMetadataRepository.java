package uz.uniSport.uni_sport.repository.files;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.uniSport.uni_sport.domain.files.FileMetadata;

import java.util.UUID;

/**
 * Yuklangan fayllar metama'lumotlarini (FileMetadata) boshqarish uchun Repository.
 */
@Repository
public interface FileMetadataRepository extends JpaRepository<FileMetadata, UUID> {
}
