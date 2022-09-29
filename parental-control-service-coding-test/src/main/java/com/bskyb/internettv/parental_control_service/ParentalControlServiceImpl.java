package com.bskyb.internettv.parental_control_service;

import com.bskyb.internettv.thirdparty.MovieService;
import com.bskyb.internettv.thirdparty.TechnicalFailureException;
import com.bskyb.internettv.thirdparty.TitleNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;

/**
 * @author US
 * Responsible for checking whether given movie can be watched or not.
 */
@RequiredArgsConstructor
public class ParentalControlServiceImpl implements ParentalControlService {

    private static List<String> parentalControlLevel = Arrays.asList("U", "PG", "12", "15", "18");
    private final MovieService movieService;

    public boolean canWatchMovie(String customerParentalControlLevel, String movieId) throws Exception {
        Assert.hasLength(movieId, "movieId can not be null");

        try {
            String metadataParentalControlLevel = movieService.getParentalControlLevel(movieId);
            return isMovieIsWatchable(metadataParentalControlLevel, customerParentalControlLevel);
        } catch (TechnicalFailureException technicalFaliureException) {
            return false;
        } catch (TitleNotFoundException titleNotFoundException) {
            throw new MovieNotFoundException(getMessage(movieId), titleNotFoundException);
        }
    }

    private boolean isMovieIsWatchable(String movieMetadataParentalControlLevel, String customerParentalControlLevel) {
        int movieParentalControlIndex = parentalControlLevel.indexOf(movieMetadataParentalControlLevel);
        int customerParentalControlIndex = parentalControlLevel.indexOf(customerParentalControlLevel);
        return movieParentalControlIndex <= customerParentalControlIndex;
    }

    private String getMessage(String movieId) {
        return String.format("The movie service could not find the given movie %s", movieId);
    }
}
