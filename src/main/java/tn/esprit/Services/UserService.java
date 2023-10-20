package tn.esprit.Services;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.Entities.Role;
import tn.esprit.Entities.User;
import tn.esprit.RegistrationAuth.Registration.Token.ConfirmationToken;
import tn.esprit.RegistrationAuth.Registration.Token.ConfirmationTokenRepository;
import tn.esprit.RegistrationAuth.Registration.Token.ConfirmationTokenService;
import tn.esprit.Repositories.UserRepository;


import javax.transaction.Transactional;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormatSymbols;
import java.time.LocalDateTime;
import java.util.*;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements IUserService, UserDetailsService {

    private final static String USER_NOT_FOUND_MSG =
            "user with email %s not found";

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    @Autowired
    private final UserRepository userRepository;
    private final ConfirmationTokenRepository confirmationTokenRepository;

    public static final int MAX_FAILED_ATTEMPTS = 3;

    private static final long LOCK_TIME_DURATION = 24 * 60 * 60 * 1000; // 24 hours


    @Override
    public User addUser(User u) {
        u.setPassword(bCryptPasswordEncoder.encode(u.getPassword()));
        userRepository.save(u);
        return u;
    }

    @Override
    public List<User> getUsers() {
        // return userRepository.findAll(Sort.by(Sort.Direction.DESC, "firstName"));
        return userRepository.findAll();
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public User findById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }


    @Override
    public User findByToken(String t) {
        return userRepository.findByConfirmationToken(t).orElse(null);
    }


  /*  @Override
    public void deleteUserByEmail(String email) {
        User u = userRepository.findByEmail(email).orElse(null);
        userRepository.delete(u);
    }*/

    @Override
    public void deleteUserById(Integer id) {
        User u = userRepository.findByIdUser(id).orElse(null);

        if (u != null) {
            ConfirmationToken token = confirmationTokenRepository.findByUser(u);
            if (token != null) {
                confirmationTokenRepository.delete(token);
            }
            userRepository.delete(u);
        }

    }


    @Override
    public void updateUser(User user) {
        user.setEnabled(true);
        userRepository.save(user);

    }

    @Override
    public List<User> findByFirstNameContains(String firstName) {
        return userRepository.findByFirstNameContains(firstName);
    }

    @Override
    public List<User> getRole(Role role) {
        return userRepository.getRole(role);
    }

    @Override
    public List<Object[]> countTotalUsersByRole() {
        return userRepository.countTotalUsersByRole();
    }


    /*  @Override
      public UserDetails loadUserByUsername(String email)
              throws UsernameNotFoundException {
          return userRepository.findByEmail(email)
                  .orElseThrow(() ->
                          new UsernameNotFoundException(
                                  String.format(USER_NOT_FOUND_MSG, email)));
      }*/
    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            log.info("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else if (!user.isEnabled()) {
            log.info("You need To Confirm your email: {}", email);
            throw new UsernameNotFoundException("You need To Confirm your email");
        } else {
            log.info("User found in the database: {}", email);
        }
        List<SimpleGrantedAuthority> authorities = getUserAuthority(user.getRole().name());
        System.out.println(user.getUsername());

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    private List<SimpleGrantedAuthority> getUserAuthority(String userRoles) {
        Set<SimpleGrantedAuthority> roles = new HashSet<SimpleGrantedAuthority>();

        roles.add(new SimpleGrantedAuthority(userRoles));
        List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
        return grantedAuthorities;

    }

    public String signUpUser(User appUser) {
        boolean userExists = userRepository
                .findByEmail(appUser.getEmail())
                .isPresent();

        if (userExists) {
            //
            //throw new IllegalStateException("email already taken");
            User u = userRepository.findByEmail(appUser.getEmail()).orElse(null);
            if (u.isEnabled() == false) {
                userRepository.save(u);
                String token = UUID.randomUUID().toString();

                ConfirmationToken confirmationToken = new ConfirmationToken(
                        token,
                        LocalDateTime.now(),
                        LocalDateTime.now().plusMinutes(15),
                        u

                );
                confirmationTokenService.saveConfirmationToken(confirmationToken);
                return " Your email is already taken";
            } else {
                return "email taken";
            }
        }
        String encodedPassword = bCryptPasswordEncoder
                .encode(appUser.getPassword());

        appUser.setPassword(encodedPassword);

        userRepository.save(appUser);

        String token = UUID.randomUUID().toString();
        // TODO : send confirmation token
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                appUser
        );


        confirmationTokenService.saveConfirmationToken(
                confirmationToken
        );

        //TODO: SEND EMAIL
        return token;

    }

    public int enableUser(String email) {
        return userRepository.enableUser(email);
    }


    ///advanced functions
    @Scheduled(cron = "0 */30 * * * *") //chaque 30 min
    public void deleteInactiveAccountsUsers() {
        LocalDateTime oneHourAgo = LocalDateTime.now().minusMinutes(30);
        List<User> inactiveUsers = userRepository.findByEnabledFalseAndCreatedAtBefore(oneHourAgo);
        for (User user : inactiveUsers) {
            userRepository.delete(user);
            System.out.println("Deleted user " + user.getUsername() + " " + user.getEmail());
        }
    }


    @Scheduled(cron = "0 */30 * * * *") //chaque 30 min
    public void deleteInactiveUsers() {
        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);
       // LocalDateTime oneMonthAgo = LocalDateTime.now().minusMinutes(1);
        List<User> inactiveUsers = userRepository.findInactiveUsers(oneMonthAgo);
        for (User user : inactiveUsers) {
            userRepository.setActivationStatusToFALSE(user.getEmail());
            System.out.println("Inactive user " + user.getUsername() + " " + user.getEmail());

        }
    }


    @Scheduled(cron = "0 * */6 * * *") //chaque 6 min
    public void unlockExpiredUsers() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expirationTime = now.minusMinutes(5);
        List<User> expiredUsers = userRepository.findExpiredLockUsers(expirationTime);
        for (User user : expiredUsers) {
            user.setLocked(false);
            user.setLockTime(null);
            user.setLoginAttempts(0);
        }
    }


    public Map<String, Double> getRoleStatistics() {
        List<User> users = userRepository.findAll();
        Map<String, Integer> roleCounts = new HashMap<>();
        for (User user : users) {
            String role = user.getRole().toString();
            roleCounts.put(role, roleCounts.getOrDefault(role, 0) + 1);
        }
        int total = users.size();
        Map<String, Double> rolePercentages = new HashMap<>();
        for (Map.Entry<String, Integer> entry : roleCounts.entrySet()) {
            String role = entry.getKey();
            int count = entry.getValue();
            double percentage = count * 100.0 / total;
            rolePercentages.put(role, percentage);
        }
        return rolePercentages;
    }

    @Override
    public Map<String, Double> getGenderStatistics() {
        List<User> users = userRepository.findAll();
        Map<String, Integer> genderCounts = new HashMap<>();
        for (User user : users) {
            String gender = user.getGender().toString();
            genderCounts.put(gender, genderCounts.getOrDefault(gender, 0) + 1);
        }
        int total1 = users.size();
        Map<String, Double> genderPercentages = new HashMap<>();
        for (Map.Entry<String, Integer> entry : genderCounts.entrySet()) {
            String gender = entry.getKey();
            int count = entry.getValue();
            double percentage = count * 100.0 / total1;
            genderPercentages.put(gender, percentage);
        }
        return genderPercentages;
    }

    @Override
    public Map<String, Double> getActivationStatusStatistics() {
        List<User> users = userRepository.findAll();
        Map<String, Integer> activationCounts = new HashMap<>();
        for (User user : users) {
            String actS = user.getActivationStatus().toString();
            activationCounts.put(actS, activationCounts.getOrDefault(actS, 0) + 1);
        }
        int total1 = users.size();
        Map<String, Double> actSPercentages = new HashMap<>();
        for (Map.Entry<String, Integer> entry : activationCounts.entrySet()) {
            String status = entry.getKey();
            if (status.equals("true")) {
                status = "Active";
            } else if (status.equals("false")) {
                status = "Inactive";
            }
            int count = entry.getValue();
            double percentage = count * 100.0 / total1;
            actSPercentages.put(status, percentage);
        }
        return actSPercentages;
    }



    @Override
    public Map<String, Map<String, Integer>> getUserCreatedAtStat() {
        List<User> users = userRepository.findAll();
        Map<String, Map<String, Integer>> statistics = new HashMap<>();
        for (User user : users) {
            LocalDateTime createdAt = user.getCreatedAt();
            int year = createdAt.getYear();
            int month = createdAt.getMonthValue();
            String yearStr = Integer.toString(year);
            String monthStr = String.format("%02d", month);
            if (!statistics.containsKey(yearStr)) {
                // Initialize year statistics with all 12 months
                Map<String, Integer> yearStatistics = new HashMap<>();
                for (int i = 1; i <= 12; i++) {
                    String monthName = new DateFormatSymbols().getMonths()[i-1];
                    yearStatistics.put(monthName, 0);
                }
                statistics.put(yearStr, yearStatistics);
            }
            Map<String, Integer> yearStatistics = statistics.get(yearStr);
            int count = yearStatistics.get(new DateFormatSymbols().getMonths()[month-1]);
            yearStatistics.put(new DateFormatSymbols().getMonths()[month-1], count + 1);
        }
        return statistics;
    }





}
