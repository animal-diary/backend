package animal.diary.code;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VitalCategoryTest {

    @Test
    void testFromValidRR() {
        // Given & When
        VitalCategory result = VitalCategory.from("rr");
        
        // Then
        assertEquals(VitalCategory.RR, result);
    }

    @Test
    void testFromValidRRUpperCase() {
        // Given & When
        VitalCategory result = VitalCategory.from("RR");
        
        // Then
        assertEquals(VitalCategory.RR, result);
    }

    @Test
    void testFromValidHeartRate() {
        // Given & When
        VitalCategory result = VitalCategory.from("heart-rate");
        
        // Then
        assertEquals(VitalCategory.HEART_RATE, result);
    }

    @Test
    void testFromValidHeartRateUpperCase() {
        // Given & When
        VitalCategory result = VitalCategory.from("HEART-RATE");
        
        // Then
        assertEquals(VitalCategory.HEART_RATE, result);
    }

    @Test
    void testFromInvalidCategory() {
        // Given & When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            VitalCategory.from("invalid-category");
        });
        
        assertEquals("Unknown category: invalid-category", exception.getMessage());
    }

    @Test
    void testFromNullCategory() {
        // Given & When & Then
        assertThrows(NullPointerException.class, () -> {
            VitalCategory.from(null);
        });
    }

    @Test
    void testFromEmptyCategory() {
        // Given & When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            VitalCategory.from("");
        });
        
        assertEquals("Unknown category: ", exception.getMessage());
    }

    @Test
    void testEnumValues() {
        // Given & When
        VitalCategory[] values = VitalCategory.values();
        
        // Then
        assertEquals(2, values.length);
        assertTrue(java.util.Arrays.asList(values).contains(VitalCategory.RR));
        assertTrue(java.util.Arrays.asList(values).contains(VitalCategory.HEART_RATE));
    }
}