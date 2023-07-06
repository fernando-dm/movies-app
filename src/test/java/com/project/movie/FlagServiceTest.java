package com.project.movie;

import com.project.utils.toggles.Flag;
import com.project.utils.toggles.FlagContext;
import com.project.utils.toggles.FlagService;
import org.junit.jupiter.api.Test;

class FlagServiceTest {

    @Test
    void isEnabled() {
        FlagService flagService = new FlagService();
        Flag flag = getFlag();

        FlagContext context = getContext();

        flagService.isEnabled(flag,context);

    }

    private static FlagContext getContext() {
        return new FlagContext() {
            @Override
            public String companyId() {
                return "1";
            }

            @Override
            public int tenant() {
                return 0;
            }
        };
    }

    private static Flag getFlag() {
        return new Flag() {
            @Override
            public String getName() {
                return "cafe";
            }

            @Override
            public boolean getDefault() {
                return false;
            }
        };
    }

    /*
    * flag, para 1 tenant y 1 compannia, true para esa compannia-tenant y false para el resto
    * flag para 1 tenant (ejemplo OCA) y true para todas las companias de ese tenant
    * flag prendida para todos ... (todos tenant, todas las companias)
    *
    * se cayo unleash .... vamos por default
    * */
}