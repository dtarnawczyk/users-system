@GenericGenerators({
        @GenericGenerator(
                name = Constants.ID_GENERATOR,
                strategy = "enhanced-sequence",
                parameters = {
                        @Parameter(
                                name = "sequence_name",
                                value = Constants.ID_GENERATOR_SEQUENCE_NAME
                        ),
                        @Parameter(
                                name = "initial_value",
                                value = "1000"
                        ),
                        @Parameter(
                                name = "increment_size",
                                value = "1"
                        ),
                        @Parameter(
                                name = "optimizer",
                                value = "pooled-lo"
                        )
                })
})
package org.customers.system.domain.model;

import org.customers.system.domain.util.Constants;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.GenericGenerators;
import org.hibernate.annotations.Parameter;