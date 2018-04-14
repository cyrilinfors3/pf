package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.PfApp;

import com.mycompany.myapp.domain.Userextra;
import com.mycompany.myapp.repository.UserextraRepository;
import com.mycompany.myapp.repository.search.UserextraSearchRepository;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the UserextraResource REST controller.
 *
 * @see UserextraResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PfApp.class)
public class UserextraResourceIntTest {

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_CONTENT_TYPE = "image/png";

    @Autowired
    private UserextraRepository userextraRepository;

    @Autowired
    private UserextraSearchRepository userextraSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserextraMockMvc;

    private Userextra userextra;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserextraResource userextraResource = new UserextraResource(userextraRepository, userextraSearchRepository);
        this.restUserextraMockMvc = MockMvcBuilders.standaloneSetup(userextraResource)
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
    public static Userextra createEntity(EntityManager em) {
        Userextra userextra = new Userextra()
            .phone(DEFAULT_PHONE)
            .photo(DEFAULT_PHOTO)
            .photoContentType(DEFAULT_PHOTO_CONTENT_TYPE);
        return userextra;
    }

    @Before
    public void initTest() {
        userextraSearchRepository.deleteAll();
        userextra = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserextra() throws Exception {
        int databaseSizeBeforeCreate = userextraRepository.findAll().size();

        // Create the Userextra
        restUserextraMockMvc.perform(post("/api/userextras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userextra)))
            .andExpect(status().isCreated());

        // Validate the Userextra in the database
        List<Userextra> userextraList = userextraRepository.findAll();
        assertThat(userextraList).hasSize(databaseSizeBeforeCreate + 1);
        Userextra testUserextra = userextraList.get(userextraList.size() - 1);
        assertThat(testUserextra.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testUserextra.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testUserextra.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);

        // Validate the Userextra in Elasticsearch
        Userextra userextraEs = userextraSearchRepository.findOne(testUserextra.getId());
        assertThat(userextraEs).isEqualToComparingFieldByField(testUserextra);
    }

    @Test
    @Transactional
    public void createUserextraWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userextraRepository.findAll().size();

        // Create the Userextra with an existing ID
        userextra.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserextraMockMvc.perform(post("/api/userextras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userextra)))
            .andExpect(status().isBadRequest());

        // Validate the Userextra in the database
        List<Userextra> userextraList = userextraRepository.findAll();
        assertThat(userextraList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUserextras() throws Exception {
        // Initialize the database
        userextraRepository.saveAndFlush(userextra);

        // Get all the userextraList
        restUserextraMockMvc.perform(get("/api/userextras?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userextra.getId().intValue())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))));
    }

    @Test
    @Transactional
    public void getUserextra() throws Exception {
        // Initialize the database
        userextraRepository.saveAndFlush(userextra);

        // Get the userextra
        restUserextraMockMvc.perform(get("/api/userextras/{id}", userextra.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userextra.getId().intValue()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.photoContentType").value(DEFAULT_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo").value(Base64Utils.encodeToString(DEFAULT_PHOTO)));
    }

    @Test
    @Transactional
    public void getNonExistingUserextra() throws Exception {
        // Get the userextra
        restUserextraMockMvc.perform(get("/api/userextras/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserextra() throws Exception {
        // Initialize the database
        userextraRepository.saveAndFlush(userextra);
        userextraSearchRepository.save(userextra);
        int databaseSizeBeforeUpdate = userextraRepository.findAll().size();

        // Update the userextra
        Userextra updatedUserextra = userextraRepository.findOne(userextra.getId());
        // Disconnect from session so that the updates on updatedUserextra are not directly saved in db
        em.detach(updatedUserextra);
        updatedUserextra
            .phone(UPDATED_PHONE)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE);

        restUserextraMockMvc.perform(put("/api/userextras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserextra)))
            .andExpect(status().isOk());

        // Validate the Userextra in the database
        List<Userextra> userextraList = userextraRepository.findAll();
        assertThat(userextraList).hasSize(databaseSizeBeforeUpdate);
        Userextra testUserextra = userextraList.get(userextraList.size() - 1);
        assertThat(testUserextra.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testUserextra.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testUserextra.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);

        // Validate the Userextra in Elasticsearch
        Userextra userextraEs = userextraSearchRepository.findOne(testUserextra.getId());
        assertThat(userextraEs).isEqualToComparingFieldByField(testUserextra);
    }

    @Test
    @Transactional
    public void updateNonExistingUserextra() throws Exception {
        int databaseSizeBeforeUpdate = userextraRepository.findAll().size();

        // Create the Userextra

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserextraMockMvc.perform(put("/api/userextras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userextra)))
            .andExpect(status().isCreated());

        // Validate the Userextra in the database
        List<Userextra> userextraList = userextraRepository.findAll();
        assertThat(userextraList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUserextra() throws Exception {
        // Initialize the database
        userextraRepository.saveAndFlush(userextra);
        userextraSearchRepository.save(userextra);
        int databaseSizeBeforeDelete = userextraRepository.findAll().size();

        // Get the userextra
        restUserextraMockMvc.perform(delete("/api/userextras/{id}", userextra.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean userextraExistsInEs = userextraSearchRepository.exists(userextra.getId());
        assertThat(userextraExistsInEs).isFalse();

        // Validate the database is empty
        List<Userextra> userextraList = userextraRepository.findAll();
        assertThat(userextraList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchUserextra() throws Exception {
        // Initialize the database
        userextraRepository.saveAndFlush(userextra);
        userextraSearchRepository.save(userextra);

        // Search the userextra
        restUserextraMockMvc.perform(get("/api/_search/userextras?query=id:" + userextra.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userextra.getId().intValue())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Userextra.class);
        Userextra userextra1 = new Userextra();
        userextra1.setId(1L);
        Userextra userextra2 = new Userextra();
        userextra2.setId(userextra1.getId());
        assertThat(userextra1).isEqualTo(userextra2);
        userextra2.setId(2L);
        assertThat(userextra1).isNotEqualTo(userextra2);
        userextra1.setId(null);
        assertThat(userextra1).isNotEqualTo(userextra2);
    }
}
