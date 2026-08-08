#!/bin/bash

# Fix Mapping import in AIMapper
sed -i '' 's/import org.mapstruct.MappingConstants;/import org.mapstruct.Mapping;\nimport org.mapstruct.MappingConstants;/' src/main/java/uz/uniSport/uni_sport/mapper/ai/AIMapper.java

# Fix Long userId -> UUID userId in files
find src/main/java/uz/uniSport/uni_sport/dto -type f \( -name "WorkoutPlan*.java" -o -name "DynamicQRCode*.java" -o -name "TokenUsageLog*.java" \) -exec sed -i '' 's/Long userId/UUID userId/g' {} +
find src/main/java/uz/uniSport/uni_sport/repository -type f \( -name "WorkoutPlan*.java" -o -name "DynamicQRCode*.java" -o -name "TokenUsageLog*.java" \) -exec sed -i '' 's/Long userId/UUID userId/g' {} +
find src/main/java/uz/uniSport/uni_sport/service -type f \( -name "WorkoutPlan*.java" -o -name "DynamicQRCode*.java" -o -name "TokenUsageLog*.java" \) -exec sed -i '' 's/Long userId/UUID userId/g' {} +
find src/main/java/uz/uniSport/uni_sport/controller -type f \( -name "WorkoutPlan*.java" -o -name "DynamicQRCode*.java" -o -name "TokenUsageLog*.java" \) -exec sed -i '' 's/Long userId/UUID userId/g' {} +

# Add UUID imports if missing
for f in $(find src/main/java/uz/uniSport/uni_sport -type f \( -name "WorkoutPlan*.java" -o -name "DynamicQRCode*.java" -o -name "TokenUsageLog*.java" \)); do
    if ! grep -q "import java.util.UUID;" "$f"; then
        sed -i '' '/package /a\
import java.util.UUID;
' "$f"
    fi
done

mvn clean package -DskipTests
