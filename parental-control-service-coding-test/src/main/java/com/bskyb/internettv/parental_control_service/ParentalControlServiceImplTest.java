package com.bskyb.internettv.parental_control_service;

import com.bskyb.internettv.thirdparty.MovieService;
import com.bskyb.internettv.thirdparty.TechnicalFailureException;
import com.bskyb.internettv.thirdparty.TitleNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;


/**
 * @author US
 * Unit tests for ParentalControlService
 */

@RunWith(MockitoJUnitRunner.class)
public class ParentalControlServiceImplTest {
    private static final String MOVIE_TITLE_ID = "testMovieId";

    @InjectMocks
    private ParentalControlServiceImpl pcServiceImpl;

    @Mock
    private MovieService movieService;

    @Test(expected = IllegalArgumentException.class)
    public void canWatchMovieThrowsErrorWhenGivenMovieIdIsNull() throws Exception {
        pcServiceImpl.canWatchMovie("12", null);

        verifyNoInteractions(movieService);
    }

    @Test(expected = IllegalArgumentException.class)
    public void canWatchMovieThrowsErrorWhenGivenMovieIdIsEmpty() throws Exception {
        pcServiceImpl.canWatchMovie("12", "");

        verifyNoInteractions(movieService);
    }

    //check the scenario expectation when client supplies null customer parental control level to ParentalControlService
    @Test
    public void canWatchMovieReturnsTrueWhenGivenCustomerParentalControlLevelIsNull() throws Exception {
        when(movieService.getParentalControlLevel(MOVIE_TITLE_ID)).thenReturn("U");
        boolean result = pcServiceImpl.canWatchMovie(null, MOVIE_TITLE_ID);

        assertThat(result, is(false));
    }

    //check the scenario expectation when movie service returns null parental control level
    @Test
    public void canWatchMovieReturnsTrueWhenMovieMetadataServiceParentalControlLevelIsNull() throws Exception {
        when(movieService.getParentalControlLevel(MOVIE_TITLE_ID)).thenReturn(null);
        boolean result = pcServiceImpl.canWatchMovie("U", MOVIE_TITLE_ID);

        assertThat(result, is(true));
    }

    @Test
    public void canWatchMovieReturnsFalseWhenMovieServiceThrowsTechnicalFailureException() throws Exception {
        when(movieService.getParentalControlLevel(MOVIE_TITLE_ID)).thenThrow(new TechnicalFailureException());

        boolean result = pcServiceImpl.canWatchMovie("18", MOVIE_TITLE_ID);

        assertThat(result, is(false));
        verify(movieService, times(1)).getParentalControlLevel(MOVIE_TITLE_ID);
    }

    @Test(expected = MovieNotFoundException.class)
    public void canWatchMovieReturnsErrorToClientWhenMovieServiceThrowsTitleNotFoundException() throws Exception {
        when(movieService.getParentalControlLevel(MOVIE_TITLE_ID)).thenThrow(new TitleNotFoundException());

        pcServiceImpl.canWatchMovie("18", MOVIE_TITLE_ID);
        verify(movieService, times(1)).getParentalControlLevel(MOVIE_TITLE_ID);
    }

    // When movie parental control level is equal to customer parental control level
    @Test
    public void canWatchMovieReturnsTrueWhenMovieParentalControlLevelPCIsEqualToCustomerParentalControlLevelPC() throws Exception {
        when(movieService.getParentalControlLevel(MOVIE_TITLE_ID)).thenReturn("U");

        boolean result = pcServiceImpl.canWatchMovie("U", MOVIE_TITLE_ID);
        assertThat(result, is(true));
    }

    // When movie parental control level U is less than customer parental control level PG
    @Test
    public void canWatchMovieReturnsTrueWhenMovieParentalControlLevelUIsLessThanCustomerParentalControlLevelPG() throws Exception {
        when(movieService.getParentalControlLevel(MOVIE_TITLE_ID)).thenReturn("U");

        boolean result = pcServiceImpl.canWatchMovie("PG", MOVIE_TITLE_ID);
        assertThat(result, is(true));
    }

    // When movie parental control level PG is less than customer parental control level 12
    @Test
    public void canWatchMovieReturnsTrueWhenMovieParentalControlLevelPGIsLessThanCustomerParentalControlLevel12() throws Exception {
        when(movieService.getParentalControlLevel(MOVIE_TITLE_ID)).thenReturn("PG");

        boolean result = pcServiceImpl.canWatchMovie("12", MOVIE_TITLE_ID);
        assertThat(result, is(true));
    }

    // When movie parental control level 12 is less than customer parental control level 15
    @Test
    public void canWatchMovieReturnsTrueWhenMovieParentalControlLevel12IsLessThanCustomerParentalControlLevel15() throws Exception {
        when(movieService.getParentalControlLevel(MOVIE_TITLE_ID)).thenReturn("12");

        boolean result = pcServiceImpl.canWatchMovie("15", MOVIE_TITLE_ID);
        assertThat(result, is(true));
    }

    // When movie  parental control level is 15 less than customer  parental control level 18
    @Test
    public void canWatchMovieReturnsTrueWhenMovieParentalControlLevel15IsLessThanCustomerParentalControl18()
            throws Exception {
        when(movieService.getParentalControlLevel(MOVIE_TITLE_ID)).thenReturn("15");

        boolean result = pcServiceImpl.canWatchMovie("18", MOVIE_TITLE_ID);
        assertThat(result, is(true));
    }

    // When movie parental control level is PG is greater than customer parental control level U
    @Test
    public void canWatchMovieReturnsFalseWhenMovieParentalControlLevelPGIsGreaterThanTheCustomerParentalControlU()
            throws Exception {
        when(movieService.getParentalControlLevel(MOVIE_TITLE_ID)).thenReturn("PG");

        boolean result = pcServiceImpl.canWatchMovie("U", MOVIE_TITLE_ID);
        assertThat(result, is(false));
    }

    // When movie parental control level is 12 is greater than customer parental control level PG
    @Test
    public void canWatchMovieReturnsFalseWhenMovieParentalControlLevel12IsGreaterThanTheCustomerParentalControlPG()
            throws Exception {
        when(movieService.getParentalControlLevel(MOVIE_TITLE_ID)).thenReturn("12");

        boolean result = pcServiceImpl.canWatchMovie("PG", MOVIE_TITLE_ID);
        assertThat(result, is(false));
    }

    // When movie parental control level is 15 is greater than customer PC level 12
    @Test
    public void canWatchMovieReturnsFalseWhenMovieParentalControlLevel15IsGreaterThanTheCustomerParentalControl12()
            throws Exception {
        when(movieService.getParentalControlLevel(MOVIE_TITLE_ID)).thenReturn("15");

        boolean result = pcServiceImpl.canWatchMovie("12", MOVIE_TITLE_ID);
        assertThat(result, is(false));
    }

    // When movie parental control level is 18 is greater than customer PC level 15
    @Test
    public void canWatchMovieReturnsFalseWhenMovieParentalControlLevel18IsGreaterThanTheCustomerParentalControl15()
            throws Exception {
        when(movieService.getParentalControlLevel(MOVIE_TITLE_ID)).thenReturn("18");

        boolean result4 = pcServiceImpl.canWatchMovie("15", MOVIE_TITLE_ID);
        assertThat(result4, is(false));
    }


}