package com.aspire.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Validation注解
 *
 * @author JustryDeng
 * @date 2019/1/15 0:43
 */

public class ValidationBeanModel {

    @Setter
    @Getter
    public class AbcAssertFalse {

        @AssertFalse
        private Boolean myAssertFalse;
    }

    @Setter
    @Getter
    public class AbcAssertTrue {

        @AssertTrue
        private Boolean myAssertTrue;
    }

    @Setter
    @Getter
    public class AbcDecimalMax {

        @DecimalMax(value = "12.3")
        private String myDecimalMax;
    }

    @Setter
    @Getter
    public class AbcDecimalMin {

        @DecimalMin(value = "10.3")
        private String myDecimalMin;
    }

    @Setter
    @Getter
    public class AbcDigits {

        @Digits(integer = 5, fraction = 3)
        private Integer myDigits;
    }

    @Setter
    @Getter
    public class AbcEmail {

        @Email
        private String myEmail;
    }

    @Setter
    @Getter
    public class AbcFuture {

        @Future
        private Date myFuture;
    }

    @Setter
    @Getter
    public class AbcLength {

        @Length(min = 5, max = 10)
        private String myLength;
    }

    @Setter
    @Getter
    public class AbcMax {

        @Max(value = 200)
        private Long myMax;
    }

    @Setter
    @Getter
    public class AbcMin {

        @Min(value = 100)
        private Long myMin;
    }

    @Setter
    @Getter
    public class AbcNotBlank {

        @NotBlank
        private String myStringNotBlank;

        @NotBlank
        private String myObjNotBlank;
    }

    @Setter
    @Getter
    public class AbcNotEmpty {

        @NotEmpty
        private String myStringNotEmpty;

        @NotEmpty
        private String myNullNotEmpty;

        @NotEmpty
        private Map<String, Object> myMapNotEmpty;

        @NotEmpty
        private List<Object> myListNotEmpty;

        @NotEmpty
        private Object[] myArrayNotEmpty;
    }

    @Setter
    @Getter
    public class AbcNotNull {

        @NotNull
        private String myStringNotNull;

        @NotNull
        private Object myNullNotNull;

        @NotNull
        private Map<String, Object> myMapNotNull;
    }

    @Setter
    @Getter
    public class AbcNull {

        @Null
        private String myStringNull;

        @Null
        private Map<String, Object> myMapNull;
    }

    @Setter
    @Getter
    public class AbcPast {

        @Past
        private Date myPast;
    }

    @Setter
    @Getter
    public class AbcPattern {

        @Pattern(regexp = "\\d+")
        private String myPattern;
    }

    @Setter
    @Getter
    public class AbcRange {

        @Range(min = 100, max = 100000000000L)
        private Double myRange;
    }

    @Setter
    @Getter
    public class AbcSize {

        @Size(min = 3, max = 5)
        private List<Integer> mySize;
    }

    @Setter
    @Getter
    public class AbcUrl {

        @URL
        private String myURL;
    }
}
