package com.winning.familydoctor.web.rest;

import com.winning.familydoctor.FamilyDoctorServerApp;

import com.winning.familydoctor.domain.BasicService;
import com.winning.familydoctor.repository.BasicServiceRepository;
import com.winning.familydoctor.service.BasicServiceService;
import com.winning.familydoctor.web.rest.errors.ExceptionTranslator;
import com.winning.familydoctor.service.dto.BasicServiceCriteria;
import com.winning.familydoctor.service.BasicServiceQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static com.winning.familydoctor.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.winning.familydoctor.domain.enumeration.ServiceType;
import com.winning.familydoctor.domain.enumeration.AppraiseType;
import com.winning.familydoctor.domain.enumeration.Status;
/**
 * Test class for the BasicServiceResource REST controller.
 *
 * @see BasicServiceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FamilyDoctorServerApp.class)
public class BasicServiceResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SEARCH_NO = "AAAAAAAAAA";
    private static final String UPDATED_SEARCH_NO = "BBBBBBBBBB";

    private static final ServiceType DEFAULT_SERVICE_TYPE = ServiceType.JI_BEN_ZHEN_LIAO;
    private static final ServiceType UPDATED_SERVICE_TYPE = ServiceType.GONG_GONG_FU_WU;

    private static final AppraiseType DEFAULT_APPRAISE_TYPE = AppraiseType.SHOU_DONG;
    private static final AppraiseType UPDATED_APPRAISE_TYPE = AppraiseType.ZI_DONG;

    private static final Integer DEFAULT_APPRAISE_VALUE = 0;
    private static final Integer UPDATED_APPRAISE_VALUE = 1;

    private static final Double DEFAULT_REFERENCE_PRICE = 0D;
    private static final Double UPDATED_REFERENCE_PRICE = 1D;

    private static final Double DEFAULT_SUBSIDY_PRICE = 0D;
    private static final Double UPDATED_SUBSIDY_PRICE = 1D;

    private static final Integer DEFAULT_SERVICE_COUNT = 0;
    private static final Integer UPDATED_SERVICE_COUNT = 1;

    private static final Boolean DEFAULT_CAN_APPOINTMENT = false;
    private static final Boolean UPDATED_CAN_APPOINTMENT = true;

    private static final Integer DEFAULT_ORDER = 0;
    private static final Integer UPDATED_ORDER = 1;

    private static final Status DEFAULT_STATUS = Status.QI_DONG;
    private static final Status UPDATED_STATUS = Status.TING_ZHI;

    private static final String DEFAULT_DESC = "AAAAAAAAAA";
    private static final String UPDATED_DESC = "BBBBBBBBBB";

    @Autowired
    private BasicServiceRepository basicServiceRepository;
    
    @Autowired
    private BasicServiceService basicServiceService;

    @Autowired
    private BasicServiceQueryService basicServiceQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBasicServiceMockMvc;

    private BasicService basicService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BasicServiceResource basicServiceResource = new BasicServiceResource(basicServiceService, basicServiceQueryService);
        this.restBasicServiceMockMvc = MockMvcBuilders.standaloneSetup(basicServiceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BasicService createEntity(EntityManager em) {
        BasicService basicService = new BasicService()
            .name(DEFAULT_NAME)
            .searchNo(DEFAULT_SEARCH_NO)
            .serviceType(DEFAULT_SERVICE_TYPE)
            .appraiseType(DEFAULT_APPRAISE_TYPE)
            .appraiseValue(DEFAULT_APPRAISE_VALUE)
            .referencePrice(DEFAULT_REFERENCE_PRICE)
            .subsidyPrice(DEFAULT_SUBSIDY_PRICE)
            .serviceCount(DEFAULT_SERVICE_COUNT)
            .canAppointment(DEFAULT_CAN_APPOINTMENT)
            .order(DEFAULT_ORDER)
            .status(DEFAULT_STATUS)
            .desc(DEFAULT_DESC);
        return basicService;
    }

    @Before
    public void initTest() {
        basicService = createEntity(em);
    }

    @Test
    @Transactional
    public void createBasicService() throws Exception {
        int databaseSizeBeforeCreate = basicServiceRepository.findAll().size();

        // Create the BasicService
        restBasicServiceMockMvc.perform(post("/api/basic-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(basicService)))
            .andExpect(status().isCreated());

        // Validate the BasicService in the database
        List<BasicService> basicServiceList = basicServiceRepository.findAll();
        assertThat(basicServiceList).hasSize(databaseSizeBeforeCreate + 1);
        BasicService testBasicService = basicServiceList.get(basicServiceList.size() - 1);
        assertThat(testBasicService.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBasicService.getSearchNo()).isEqualTo(DEFAULT_SEARCH_NO);
        assertThat(testBasicService.getServiceType()).isEqualTo(DEFAULT_SERVICE_TYPE);
        assertThat(testBasicService.getAppraiseType()).isEqualTo(DEFAULT_APPRAISE_TYPE);
        assertThat(testBasicService.getAppraiseValue()).isEqualTo(DEFAULT_APPRAISE_VALUE);
        assertThat(testBasicService.getReferencePrice()).isEqualTo(DEFAULT_REFERENCE_PRICE);
        assertThat(testBasicService.getSubsidyPrice()).isEqualTo(DEFAULT_SUBSIDY_PRICE);
        assertThat(testBasicService.getServiceCount()).isEqualTo(DEFAULT_SERVICE_COUNT);
        assertThat(testBasicService.isCanAppointment()).isEqualTo(DEFAULT_CAN_APPOINTMENT);
        assertThat(testBasicService.getOrder()).isEqualTo(DEFAULT_ORDER);
        assertThat(testBasicService.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testBasicService.getDesc()).isEqualTo(DEFAULT_DESC);
    }

    @Test
    @Transactional
    public void createBasicServiceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = basicServiceRepository.findAll().size();

        // Create the BasicService with an existing ID
        basicService.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBasicServiceMockMvc.perform(post("/api/basic-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(basicService)))
            .andExpect(status().isBadRequest());

        // Validate the BasicService in the database
        List<BasicService> basicServiceList = basicServiceRepository.findAll();
        assertThat(basicServiceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = basicServiceRepository.findAll().size();
        // set the field null
        basicService.setName(null);

        // Create the BasicService, which fails.

        restBasicServiceMockMvc.perform(post("/api/basic-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(basicService)))
            .andExpect(status().isBadRequest());

        List<BasicService> basicServiceList = basicServiceRepository.findAll();
        assertThat(basicServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkServiceTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = basicServiceRepository.findAll().size();
        // set the field null
        basicService.setServiceType(null);

        // Create the BasicService, which fails.

        restBasicServiceMockMvc.perform(post("/api/basic-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(basicService)))
            .andExpect(status().isBadRequest());

        List<BasicService> basicServiceList = basicServiceRepository.findAll();
        assertThat(basicServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAppraiseTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = basicServiceRepository.findAll().size();
        // set the field null
        basicService.setAppraiseType(null);

        // Create the BasicService, which fails.

        restBasicServiceMockMvc.perform(post("/api/basic-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(basicService)))
            .andExpect(status().isBadRequest());

        List<BasicService> basicServiceList = basicServiceRepository.findAll();
        assertThat(basicServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBasicServices() throws Exception {
        // Initialize the database
        basicServiceRepository.saveAndFlush(basicService);

        // Get all the basicServiceList
        restBasicServiceMockMvc.perform(get("/api/basic-services?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(basicService.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].searchNo").value(hasItem(DEFAULT_SEARCH_NO.toString())))
            .andExpect(jsonPath("$.[*].serviceType").value(hasItem(DEFAULT_SERVICE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].appraiseType").value(hasItem(DEFAULT_APPRAISE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].appraiseValue").value(hasItem(DEFAULT_APPRAISE_VALUE)))
            .andExpect(jsonPath("$.[*].referencePrice").value(hasItem(DEFAULT_REFERENCE_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].subsidyPrice").value(hasItem(DEFAULT_SUBSIDY_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].serviceCount").value(hasItem(DEFAULT_SERVICE_COUNT)))
            .andExpect(jsonPath("$.[*].canAppointment").value(hasItem(DEFAULT_CAN_APPOINTMENT.booleanValue())))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC.toString())));
    }
    
    @Test
    @Transactional
    public void getBasicService() throws Exception {
        // Initialize the database
        basicServiceRepository.saveAndFlush(basicService);

        // Get the basicService
        restBasicServiceMockMvc.perform(get("/api/basic-services/{id}", basicService.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(basicService.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.searchNo").value(DEFAULT_SEARCH_NO.toString()))
            .andExpect(jsonPath("$.serviceType").value(DEFAULT_SERVICE_TYPE.toString()))
            .andExpect(jsonPath("$.appraiseType").value(DEFAULT_APPRAISE_TYPE.toString()))
            .andExpect(jsonPath("$.appraiseValue").value(DEFAULT_APPRAISE_VALUE))
            .andExpect(jsonPath("$.referencePrice").value(DEFAULT_REFERENCE_PRICE.doubleValue()))
            .andExpect(jsonPath("$.subsidyPrice").value(DEFAULT_SUBSIDY_PRICE.doubleValue()))
            .andExpect(jsonPath("$.serviceCount").value(DEFAULT_SERVICE_COUNT))
            .andExpect(jsonPath("$.canAppointment").value(DEFAULT_CAN_APPOINTMENT.booleanValue()))
            .andExpect(jsonPath("$.order").value(DEFAULT_ORDER))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.desc").value(DEFAULT_DESC.toString()));
    }

    @Test
    @Transactional
    public void getAllBasicServicesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        basicServiceRepository.saveAndFlush(basicService);

        // Get all the basicServiceList where name equals to DEFAULT_NAME
        defaultBasicServiceShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the basicServiceList where name equals to UPDATED_NAME
        defaultBasicServiceShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllBasicServicesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        basicServiceRepository.saveAndFlush(basicService);

        // Get all the basicServiceList where name in DEFAULT_NAME or UPDATED_NAME
        defaultBasicServiceShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the basicServiceList where name equals to UPDATED_NAME
        defaultBasicServiceShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllBasicServicesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        basicServiceRepository.saveAndFlush(basicService);

        // Get all the basicServiceList where name is not null
        defaultBasicServiceShouldBeFound("name.specified=true");

        // Get all the basicServiceList where name is null
        defaultBasicServiceShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllBasicServicesBySearchNoIsEqualToSomething() throws Exception {
        // Initialize the database
        basicServiceRepository.saveAndFlush(basicService);

        // Get all the basicServiceList where searchNo equals to DEFAULT_SEARCH_NO
        defaultBasicServiceShouldBeFound("searchNo.equals=" + DEFAULT_SEARCH_NO);

        // Get all the basicServiceList where searchNo equals to UPDATED_SEARCH_NO
        defaultBasicServiceShouldNotBeFound("searchNo.equals=" + UPDATED_SEARCH_NO);
    }

    @Test
    @Transactional
    public void getAllBasicServicesBySearchNoIsInShouldWork() throws Exception {
        // Initialize the database
        basicServiceRepository.saveAndFlush(basicService);

        // Get all the basicServiceList where searchNo in DEFAULT_SEARCH_NO or UPDATED_SEARCH_NO
        defaultBasicServiceShouldBeFound("searchNo.in=" + DEFAULT_SEARCH_NO + "," + UPDATED_SEARCH_NO);

        // Get all the basicServiceList where searchNo equals to UPDATED_SEARCH_NO
        defaultBasicServiceShouldNotBeFound("searchNo.in=" + UPDATED_SEARCH_NO);
    }

    @Test
    @Transactional
    public void getAllBasicServicesBySearchNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        basicServiceRepository.saveAndFlush(basicService);

        // Get all the basicServiceList where searchNo is not null
        defaultBasicServiceShouldBeFound("searchNo.specified=true");

        // Get all the basicServiceList where searchNo is null
        defaultBasicServiceShouldNotBeFound("searchNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllBasicServicesByServiceTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        basicServiceRepository.saveAndFlush(basicService);

        // Get all the basicServiceList where serviceType equals to DEFAULT_SERVICE_TYPE
        defaultBasicServiceShouldBeFound("serviceType.equals=" + DEFAULT_SERVICE_TYPE);

        // Get all the basicServiceList where serviceType equals to UPDATED_SERVICE_TYPE
        defaultBasicServiceShouldNotBeFound("serviceType.equals=" + UPDATED_SERVICE_TYPE);
    }

    @Test
    @Transactional
    public void getAllBasicServicesByServiceTypeIsInShouldWork() throws Exception {
        // Initialize the database
        basicServiceRepository.saveAndFlush(basicService);

        // Get all the basicServiceList where serviceType in DEFAULT_SERVICE_TYPE or UPDATED_SERVICE_TYPE
        defaultBasicServiceShouldBeFound("serviceType.in=" + DEFAULT_SERVICE_TYPE + "," + UPDATED_SERVICE_TYPE);

        // Get all the basicServiceList where serviceType equals to UPDATED_SERVICE_TYPE
        defaultBasicServiceShouldNotBeFound("serviceType.in=" + UPDATED_SERVICE_TYPE);
    }

    @Test
    @Transactional
    public void getAllBasicServicesByServiceTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        basicServiceRepository.saveAndFlush(basicService);

        // Get all the basicServiceList where serviceType is not null
        defaultBasicServiceShouldBeFound("serviceType.specified=true");

        // Get all the basicServiceList where serviceType is null
        defaultBasicServiceShouldNotBeFound("serviceType.specified=false");
    }

    @Test
    @Transactional
    public void getAllBasicServicesByAppraiseTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        basicServiceRepository.saveAndFlush(basicService);

        // Get all the basicServiceList where appraiseType equals to DEFAULT_APPRAISE_TYPE
        defaultBasicServiceShouldBeFound("appraiseType.equals=" + DEFAULT_APPRAISE_TYPE);

        // Get all the basicServiceList where appraiseType equals to UPDATED_APPRAISE_TYPE
        defaultBasicServiceShouldNotBeFound("appraiseType.equals=" + UPDATED_APPRAISE_TYPE);
    }

    @Test
    @Transactional
    public void getAllBasicServicesByAppraiseTypeIsInShouldWork() throws Exception {
        // Initialize the database
        basicServiceRepository.saveAndFlush(basicService);

        // Get all the basicServiceList where appraiseType in DEFAULT_APPRAISE_TYPE or UPDATED_APPRAISE_TYPE
        defaultBasicServiceShouldBeFound("appraiseType.in=" + DEFAULT_APPRAISE_TYPE + "," + UPDATED_APPRAISE_TYPE);

        // Get all the basicServiceList where appraiseType equals to UPDATED_APPRAISE_TYPE
        defaultBasicServiceShouldNotBeFound("appraiseType.in=" + UPDATED_APPRAISE_TYPE);
    }

    @Test
    @Transactional
    public void getAllBasicServicesByAppraiseTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        basicServiceRepository.saveAndFlush(basicService);

        // Get all the basicServiceList where appraiseType is not null
        defaultBasicServiceShouldBeFound("appraiseType.specified=true");

        // Get all the basicServiceList where appraiseType is null
        defaultBasicServiceShouldNotBeFound("appraiseType.specified=false");
    }

    @Test
    @Transactional
    public void getAllBasicServicesByAppraiseValueIsEqualToSomething() throws Exception {
        // Initialize the database
        basicServiceRepository.saveAndFlush(basicService);

        // Get all the basicServiceList where appraiseValue equals to DEFAULT_APPRAISE_VALUE
        defaultBasicServiceShouldBeFound("appraiseValue.equals=" + DEFAULT_APPRAISE_VALUE);

        // Get all the basicServiceList where appraiseValue equals to UPDATED_APPRAISE_VALUE
        defaultBasicServiceShouldNotBeFound("appraiseValue.equals=" + UPDATED_APPRAISE_VALUE);
    }

    @Test
    @Transactional
    public void getAllBasicServicesByAppraiseValueIsInShouldWork() throws Exception {
        // Initialize the database
        basicServiceRepository.saveAndFlush(basicService);

        // Get all the basicServiceList where appraiseValue in DEFAULT_APPRAISE_VALUE or UPDATED_APPRAISE_VALUE
        defaultBasicServiceShouldBeFound("appraiseValue.in=" + DEFAULT_APPRAISE_VALUE + "," + UPDATED_APPRAISE_VALUE);

        // Get all the basicServiceList where appraiseValue equals to UPDATED_APPRAISE_VALUE
        defaultBasicServiceShouldNotBeFound("appraiseValue.in=" + UPDATED_APPRAISE_VALUE);
    }

    @Test
    @Transactional
    public void getAllBasicServicesByAppraiseValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        basicServiceRepository.saveAndFlush(basicService);

        // Get all the basicServiceList where appraiseValue is not null
        defaultBasicServiceShouldBeFound("appraiseValue.specified=true");

        // Get all the basicServiceList where appraiseValue is null
        defaultBasicServiceShouldNotBeFound("appraiseValue.specified=false");
    }

    @Test
    @Transactional
    public void getAllBasicServicesByAppraiseValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        basicServiceRepository.saveAndFlush(basicService);

        // Get all the basicServiceList where appraiseValue greater than or equals to DEFAULT_APPRAISE_VALUE
        defaultBasicServiceShouldBeFound("appraiseValue.greaterOrEqualThan=" + DEFAULT_APPRAISE_VALUE);

        // Get all the basicServiceList where appraiseValue greater than or equals to (DEFAULT_APPRAISE_VALUE + 1)
        defaultBasicServiceShouldNotBeFound("appraiseValue.greaterOrEqualThan=" + (DEFAULT_APPRAISE_VALUE + 1));
    }

    @Test
    @Transactional
    public void getAllBasicServicesByAppraiseValueIsLessThanSomething() throws Exception {
        // Initialize the database
        basicServiceRepository.saveAndFlush(basicService);

        // Get all the basicServiceList where appraiseValue less than or equals to DEFAULT_APPRAISE_VALUE
        defaultBasicServiceShouldNotBeFound("appraiseValue.lessThan=" + DEFAULT_APPRAISE_VALUE);

        // Get all the basicServiceList where appraiseValue less than or equals to (DEFAULT_APPRAISE_VALUE + 1)
        defaultBasicServiceShouldBeFound("appraiseValue.lessThan=" + (DEFAULT_APPRAISE_VALUE + 1));
    }


    @Test
    @Transactional
    public void getAllBasicServicesByReferencePriceIsEqualToSomething() throws Exception {
        // Initialize the database
        basicServiceRepository.saveAndFlush(basicService);

        // Get all the basicServiceList where referencePrice equals to DEFAULT_REFERENCE_PRICE
        defaultBasicServiceShouldBeFound("referencePrice.equals=" + DEFAULT_REFERENCE_PRICE);

        // Get all the basicServiceList where referencePrice equals to UPDATED_REFERENCE_PRICE
        defaultBasicServiceShouldNotBeFound("referencePrice.equals=" + UPDATED_REFERENCE_PRICE);
    }

    @Test
    @Transactional
    public void getAllBasicServicesByReferencePriceIsInShouldWork() throws Exception {
        // Initialize the database
        basicServiceRepository.saveAndFlush(basicService);

        // Get all the basicServiceList where referencePrice in DEFAULT_REFERENCE_PRICE or UPDATED_REFERENCE_PRICE
        defaultBasicServiceShouldBeFound("referencePrice.in=" + DEFAULT_REFERENCE_PRICE + "," + UPDATED_REFERENCE_PRICE);

        // Get all the basicServiceList where referencePrice equals to UPDATED_REFERENCE_PRICE
        defaultBasicServiceShouldNotBeFound("referencePrice.in=" + UPDATED_REFERENCE_PRICE);
    }

    @Test
    @Transactional
    public void getAllBasicServicesByReferencePriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        basicServiceRepository.saveAndFlush(basicService);

        // Get all the basicServiceList where referencePrice is not null
        defaultBasicServiceShouldBeFound("referencePrice.specified=true");

        // Get all the basicServiceList where referencePrice is null
        defaultBasicServiceShouldNotBeFound("referencePrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllBasicServicesBySubsidyPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        basicServiceRepository.saveAndFlush(basicService);

        // Get all the basicServiceList where subsidyPrice equals to DEFAULT_SUBSIDY_PRICE
        defaultBasicServiceShouldBeFound("subsidyPrice.equals=" + DEFAULT_SUBSIDY_PRICE);

        // Get all the basicServiceList where subsidyPrice equals to UPDATED_SUBSIDY_PRICE
        defaultBasicServiceShouldNotBeFound("subsidyPrice.equals=" + UPDATED_SUBSIDY_PRICE);
    }

    @Test
    @Transactional
    public void getAllBasicServicesBySubsidyPriceIsInShouldWork() throws Exception {
        // Initialize the database
        basicServiceRepository.saveAndFlush(basicService);

        // Get all the basicServiceList where subsidyPrice in DEFAULT_SUBSIDY_PRICE or UPDATED_SUBSIDY_PRICE
        defaultBasicServiceShouldBeFound("subsidyPrice.in=" + DEFAULT_SUBSIDY_PRICE + "," + UPDATED_SUBSIDY_PRICE);

        // Get all the basicServiceList where subsidyPrice equals to UPDATED_SUBSIDY_PRICE
        defaultBasicServiceShouldNotBeFound("subsidyPrice.in=" + UPDATED_SUBSIDY_PRICE);
    }

    @Test
    @Transactional
    public void getAllBasicServicesBySubsidyPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        basicServiceRepository.saveAndFlush(basicService);

        // Get all the basicServiceList where subsidyPrice is not null
        defaultBasicServiceShouldBeFound("subsidyPrice.specified=true");

        // Get all the basicServiceList where subsidyPrice is null
        defaultBasicServiceShouldNotBeFound("subsidyPrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllBasicServicesByServiceCountIsEqualToSomething() throws Exception {
        // Initialize the database
        basicServiceRepository.saveAndFlush(basicService);

        // Get all the basicServiceList where serviceCount equals to DEFAULT_SERVICE_COUNT
        defaultBasicServiceShouldBeFound("serviceCount.equals=" + DEFAULT_SERVICE_COUNT);

        // Get all the basicServiceList where serviceCount equals to UPDATED_SERVICE_COUNT
        defaultBasicServiceShouldNotBeFound("serviceCount.equals=" + UPDATED_SERVICE_COUNT);
    }

    @Test
    @Transactional
    public void getAllBasicServicesByServiceCountIsInShouldWork() throws Exception {
        // Initialize the database
        basicServiceRepository.saveAndFlush(basicService);

        // Get all the basicServiceList where serviceCount in DEFAULT_SERVICE_COUNT or UPDATED_SERVICE_COUNT
        defaultBasicServiceShouldBeFound("serviceCount.in=" + DEFAULT_SERVICE_COUNT + "," + UPDATED_SERVICE_COUNT);

        // Get all the basicServiceList where serviceCount equals to UPDATED_SERVICE_COUNT
        defaultBasicServiceShouldNotBeFound("serviceCount.in=" + UPDATED_SERVICE_COUNT);
    }

    @Test
    @Transactional
    public void getAllBasicServicesByServiceCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        basicServiceRepository.saveAndFlush(basicService);

        // Get all the basicServiceList where serviceCount is not null
        defaultBasicServiceShouldBeFound("serviceCount.specified=true");

        // Get all the basicServiceList where serviceCount is null
        defaultBasicServiceShouldNotBeFound("serviceCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllBasicServicesByServiceCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        basicServiceRepository.saveAndFlush(basicService);

        // Get all the basicServiceList where serviceCount greater than or equals to DEFAULT_SERVICE_COUNT
        defaultBasicServiceShouldBeFound("serviceCount.greaterOrEqualThan=" + DEFAULT_SERVICE_COUNT);

        // Get all the basicServiceList where serviceCount greater than or equals to (DEFAULT_SERVICE_COUNT + 1)
        defaultBasicServiceShouldNotBeFound("serviceCount.greaterOrEqualThan=" + (DEFAULT_SERVICE_COUNT + 1));
    }

    @Test
    @Transactional
    public void getAllBasicServicesByServiceCountIsLessThanSomething() throws Exception {
        // Initialize the database
        basicServiceRepository.saveAndFlush(basicService);

        // Get all the basicServiceList where serviceCount less than or equals to DEFAULT_SERVICE_COUNT
        defaultBasicServiceShouldNotBeFound("serviceCount.lessThan=" + DEFAULT_SERVICE_COUNT);

        // Get all the basicServiceList where serviceCount less than or equals to (DEFAULT_SERVICE_COUNT + 1)
        defaultBasicServiceShouldBeFound("serviceCount.lessThan=" + (DEFAULT_SERVICE_COUNT + 1));
    }


    @Test
    @Transactional
    public void getAllBasicServicesByCanAppointmentIsEqualToSomething() throws Exception {
        // Initialize the database
        basicServiceRepository.saveAndFlush(basicService);

        // Get all the basicServiceList where canAppointment equals to DEFAULT_CAN_APPOINTMENT
        defaultBasicServiceShouldBeFound("canAppointment.equals=" + DEFAULT_CAN_APPOINTMENT);

        // Get all the basicServiceList where canAppointment equals to UPDATED_CAN_APPOINTMENT
        defaultBasicServiceShouldNotBeFound("canAppointment.equals=" + UPDATED_CAN_APPOINTMENT);
    }

    @Test
    @Transactional
    public void getAllBasicServicesByCanAppointmentIsInShouldWork() throws Exception {
        // Initialize the database
        basicServiceRepository.saveAndFlush(basicService);

        // Get all the basicServiceList where canAppointment in DEFAULT_CAN_APPOINTMENT or UPDATED_CAN_APPOINTMENT
        defaultBasicServiceShouldBeFound("canAppointment.in=" + DEFAULT_CAN_APPOINTMENT + "," + UPDATED_CAN_APPOINTMENT);

        // Get all the basicServiceList where canAppointment equals to UPDATED_CAN_APPOINTMENT
        defaultBasicServiceShouldNotBeFound("canAppointment.in=" + UPDATED_CAN_APPOINTMENT);
    }

    @Test
    @Transactional
    public void getAllBasicServicesByCanAppointmentIsNullOrNotNull() throws Exception {
        // Initialize the database
        basicServiceRepository.saveAndFlush(basicService);

        // Get all the basicServiceList where canAppointment is not null
        defaultBasicServiceShouldBeFound("canAppointment.specified=true");

        // Get all the basicServiceList where canAppointment is null
        defaultBasicServiceShouldNotBeFound("canAppointment.specified=false");
    }

    @Test
    @Transactional
    public void getAllBasicServicesByOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        basicServiceRepository.saveAndFlush(basicService);

        // Get all the basicServiceList where order equals to DEFAULT_ORDER
        defaultBasicServiceShouldBeFound("order.equals=" + DEFAULT_ORDER);

        // Get all the basicServiceList where order equals to UPDATED_ORDER
        defaultBasicServiceShouldNotBeFound("order.equals=" + UPDATED_ORDER);
    }

    @Test
    @Transactional
    public void getAllBasicServicesByOrderIsInShouldWork() throws Exception {
        // Initialize the database
        basicServiceRepository.saveAndFlush(basicService);

        // Get all the basicServiceList where order in DEFAULT_ORDER or UPDATED_ORDER
        defaultBasicServiceShouldBeFound("order.in=" + DEFAULT_ORDER + "," + UPDATED_ORDER);

        // Get all the basicServiceList where order equals to UPDATED_ORDER
        defaultBasicServiceShouldNotBeFound("order.in=" + UPDATED_ORDER);
    }

    @Test
    @Transactional
    public void getAllBasicServicesByOrderIsNullOrNotNull() throws Exception {
        // Initialize the database
        basicServiceRepository.saveAndFlush(basicService);

        // Get all the basicServiceList where order is not null
        defaultBasicServiceShouldBeFound("order.specified=true");

        // Get all the basicServiceList where order is null
        defaultBasicServiceShouldNotBeFound("order.specified=false");
    }

    @Test
    @Transactional
    public void getAllBasicServicesByOrderIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        basicServiceRepository.saveAndFlush(basicService);

        // Get all the basicServiceList where order greater than or equals to DEFAULT_ORDER
        defaultBasicServiceShouldBeFound("order.greaterOrEqualThan=" + DEFAULT_ORDER);

        // Get all the basicServiceList where order greater than or equals to UPDATED_ORDER
        defaultBasicServiceShouldNotBeFound("order.greaterOrEqualThan=" + UPDATED_ORDER);
    }

    @Test
    @Transactional
    public void getAllBasicServicesByOrderIsLessThanSomething() throws Exception {
        // Initialize the database
        basicServiceRepository.saveAndFlush(basicService);

        // Get all the basicServiceList where order less than or equals to DEFAULT_ORDER
        defaultBasicServiceShouldNotBeFound("order.lessThan=" + DEFAULT_ORDER);

        // Get all the basicServiceList where order less than or equals to UPDATED_ORDER
        defaultBasicServiceShouldBeFound("order.lessThan=" + UPDATED_ORDER);
    }


    @Test
    @Transactional
    public void getAllBasicServicesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        basicServiceRepository.saveAndFlush(basicService);

        // Get all the basicServiceList where status equals to DEFAULT_STATUS
        defaultBasicServiceShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the basicServiceList where status equals to UPDATED_STATUS
        defaultBasicServiceShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllBasicServicesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        basicServiceRepository.saveAndFlush(basicService);

        // Get all the basicServiceList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultBasicServiceShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the basicServiceList where status equals to UPDATED_STATUS
        defaultBasicServiceShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllBasicServicesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        basicServiceRepository.saveAndFlush(basicService);

        // Get all the basicServiceList where status is not null
        defaultBasicServiceShouldBeFound("status.specified=true");

        // Get all the basicServiceList where status is null
        defaultBasicServiceShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllBasicServicesByDescIsEqualToSomething() throws Exception {
        // Initialize the database
        basicServiceRepository.saveAndFlush(basicService);

        // Get all the basicServiceList where desc equals to DEFAULT_DESC
        defaultBasicServiceShouldBeFound("desc.equals=" + DEFAULT_DESC);

        // Get all the basicServiceList where desc equals to UPDATED_DESC
        defaultBasicServiceShouldNotBeFound("desc.equals=" + UPDATED_DESC);
    }

    @Test
    @Transactional
    public void getAllBasicServicesByDescIsInShouldWork() throws Exception {
        // Initialize the database
        basicServiceRepository.saveAndFlush(basicService);

        // Get all the basicServiceList where desc in DEFAULT_DESC or UPDATED_DESC
        defaultBasicServiceShouldBeFound("desc.in=" + DEFAULT_DESC + "," + UPDATED_DESC);

        // Get all the basicServiceList where desc equals to UPDATED_DESC
        defaultBasicServiceShouldNotBeFound("desc.in=" + UPDATED_DESC);
    }

    @Test
    @Transactional
    public void getAllBasicServicesByDescIsNullOrNotNull() throws Exception {
        // Initialize the database
        basicServiceRepository.saveAndFlush(basicService);

        // Get all the basicServiceList where desc is not null
        defaultBasicServiceShouldBeFound("desc.specified=true");

        // Get all the basicServiceList where desc is null
        defaultBasicServiceShouldNotBeFound("desc.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultBasicServiceShouldBeFound(String filter) throws Exception {
        restBasicServiceMockMvc.perform(get("/api/basic-services?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(basicService.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].searchNo").value(hasItem(DEFAULT_SEARCH_NO.toString())))
            .andExpect(jsonPath("$.[*].serviceType").value(hasItem(DEFAULT_SERVICE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].appraiseType").value(hasItem(DEFAULT_APPRAISE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].appraiseValue").value(hasItem(DEFAULT_APPRAISE_VALUE)))
            .andExpect(jsonPath("$.[*].referencePrice").value(hasItem(DEFAULT_REFERENCE_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].subsidyPrice").value(hasItem(DEFAULT_SUBSIDY_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].serviceCount").value(hasItem(DEFAULT_SERVICE_COUNT)))
            .andExpect(jsonPath("$.[*].canAppointment").value(hasItem(DEFAULT_CAN_APPOINTMENT.booleanValue())))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC.toString())));

        // Check, that the count call also returns 1
        restBasicServiceMockMvc.perform(get("/api/basic-services/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultBasicServiceShouldNotBeFound(String filter) throws Exception {
        restBasicServiceMockMvc.perform(get("/api/basic-services?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBasicServiceMockMvc.perform(get("/api/basic-services/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingBasicService() throws Exception {
        // Get the basicService
        restBasicServiceMockMvc.perform(get("/api/basic-services/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBasicService() throws Exception {
        // Initialize the database
        basicServiceService.save(basicService);

        int databaseSizeBeforeUpdate = basicServiceRepository.findAll().size();

        // Update the basicService
        BasicService updatedBasicService = basicServiceRepository.findById(basicService.getId()).get();
        // Disconnect from session so that the updates on updatedBasicService are not directly saved in db
        em.detach(updatedBasicService);
        updatedBasicService
            .name(UPDATED_NAME)
            .searchNo(UPDATED_SEARCH_NO)
            .serviceType(UPDATED_SERVICE_TYPE)
            .appraiseType(UPDATED_APPRAISE_TYPE)
            .appraiseValue(UPDATED_APPRAISE_VALUE)
            .referencePrice(UPDATED_REFERENCE_PRICE)
            .subsidyPrice(UPDATED_SUBSIDY_PRICE)
            .serviceCount(UPDATED_SERVICE_COUNT)
            .canAppointment(UPDATED_CAN_APPOINTMENT)
            .order(UPDATED_ORDER)
            .status(UPDATED_STATUS)
            .desc(UPDATED_DESC);

        restBasicServiceMockMvc.perform(put("/api/basic-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBasicService)))
            .andExpect(status().isOk());

        // Validate the BasicService in the database
        List<BasicService> basicServiceList = basicServiceRepository.findAll();
        assertThat(basicServiceList).hasSize(databaseSizeBeforeUpdate);
        BasicService testBasicService = basicServiceList.get(basicServiceList.size() - 1);
        assertThat(testBasicService.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBasicService.getSearchNo()).isEqualTo(UPDATED_SEARCH_NO);
        assertThat(testBasicService.getServiceType()).isEqualTo(UPDATED_SERVICE_TYPE);
        assertThat(testBasicService.getAppraiseType()).isEqualTo(UPDATED_APPRAISE_TYPE);
        assertThat(testBasicService.getAppraiseValue()).isEqualTo(UPDATED_APPRAISE_VALUE);
        assertThat(testBasicService.getReferencePrice()).isEqualTo(UPDATED_REFERENCE_PRICE);
        assertThat(testBasicService.getSubsidyPrice()).isEqualTo(UPDATED_SUBSIDY_PRICE);
        assertThat(testBasicService.getServiceCount()).isEqualTo(UPDATED_SERVICE_COUNT);
        assertThat(testBasicService.isCanAppointment()).isEqualTo(UPDATED_CAN_APPOINTMENT);
        assertThat(testBasicService.getOrder()).isEqualTo(UPDATED_ORDER);
        assertThat(testBasicService.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testBasicService.getDesc()).isEqualTo(UPDATED_DESC);
    }

    @Test
    @Transactional
    public void updateNonExistingBasicService() throws Exception {
        int databaseSizeBeforeUpdate = basicServiceRepository.findAll().size();

        // Create the BasicService

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBasicServiceMockMvc.perform(put("/api/basic-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(basicService)))
            .andExpect(status().isBadRequest());

        // Validate the BasicService in the database
        List<BasicService> basicServiceList = basicServiceRepository.findAll();
        assertThat(basicServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBasicService() throws Exception {
        // Initialize the database
        basicServiceService.save(basicService);

        int databaseSizeBeforeDelete = basicServiceRepository.findAll().size();

        // Get the basicService
        restBasicServiceMockMvc.perform(delete("/api/basic-services/{id}", basicService.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BasicService> basicServiceList = basicServiceRepository.findAll();
        assertThat(basicServiceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BasicService.class);
        BasicService basicService1 = new BasicService();
        basicService1.setId(1L);
        BasicService basicService2 = new BasicService();
        basicService2.setId(basicService1.getId());
        assertThat(basicService1).isEqualTo(basicService2);
        basicService2.setId(2L);
        assertThat(basicService1).isNotEqualTo(basicService2);
        basicService1.setId(null);
        assertThat(basicService1).isNotEqualTo(basicService2);
    }
}
