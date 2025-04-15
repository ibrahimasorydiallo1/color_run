// Fonction pour initialiser l'application
document.addEventListener('DOMContentLoaded', () => {
    initNavigation();
    initForms();
    initRaceCards();
    initFilters();
    initNotifications();
});

// Fonction pour initialiser le menu mobile
function initMobileMenu() {
    const mobileMenuButton = document.querySelector('.mobile-menu-button');
    const mobileMenuClose = document.querySelector('.mobile-menu-close');
    const mobileMenu = document.querySelector('.mobile-menu');
    
    if (mobileMenuButton && mobileMenuClose && mobileMenu) {
        mobileMenuButton.addEventListener('click', () => {
            mobileMenu.classList.remove('hidden');
            mobileMenu.classList.add('active');
        });

        mobileMenuClose.addEventListener('click', () => {
            mobileMenu.classList.remove('active');
            setTimeout(() => {
                mobileMenu.classList.add('hidden');
            }, 300);
        });
    }
    
    // Gérer la navigation mobile
    const mobileNavLinks = document.querySelectorAll('.mobile-nav a');
    mobileNavLinks.forEach(link => {
        link.addEventListener('click', function(e) {
            // Supprimer la classe active de tous les liens
            mobileNavLinks.forEach(l => {
                l.classList.remove('text-purple-600');
                l.classList.add('text-gray-600');
            });
            
            // Ajouter la classe active au lien cliqué
            this.classList.remove('text-gray-600');
            this.classList.add('text-purple-600');
        });
    });
}

// Fonction pour initialiser les formulaires
function initForms() {
    // Formulaire de connexion
    const loginForm = document.getElementById('login-form');
    if (loginForm) {
        loginForm.addEventListener('submit', (e) => {
            e.preventDefault();
            const email = loginForm.querySelector('[name="email"]').value;
            const password = loginForm.querySelector('[name="password"]').value;
            
            // Simulation de connexion
            const user = {
                id: 1,
                name: 'John Doe',
                email: email,
                role: 'user'
            };
            
            localStorage.setItem('user', JSON.stringify(user));
            showNotification('Connexion réussie !', 'success');
            setTimeout(() => {
                window.location.href = '/dashboard.html';
            }, 1000);
        });
    }

    // Formulaire d'inscription
    const registerForm = document.getElementById('register-form');
    if (registerForm) {
        registerForm.addEventListener('submit', (e) => {
            e.preventDefault();
            const name = registerForm.querySelector('[name="name"]').value;
            const email = registerForm.querySelector('[name="email"]').value;
            const password = registerForm.querySelector('[name="password"]').value;
            
            // Simulation d'inscription
            const user = {
                id: 1,
                name: name,
                email: email,
                role: 'user'
            };
            
            localStorage.setItem('user', JSON.stringify(user));
            showNotification('Inscription réussie !', 'success');
            setTimeout(() => {
                window.location.href = '/dashboard.html';
            }, 1000);
        });
    }

    // Formulaire de contact
    const contactForm = document.getElementById('contact-form');
    if (contactForm) {
        contactForm.addEventListener('submit', (e) => {
            e.preventDefault();
            const name = contactForm.querySelector('[name="name"]').value;
            const email = contactForm.querySelector('[name="email"]').value;
            const message = contactForm.querySelector('[name="message"]').value;
            
            // Simulation d'envoi de message
            showNotification('Message envoyé avec succès !', 'success');
            contactForm.reset();
        });
    }
}

// Fonction pour initialiser les cartes de courses
function initRaceCards() {
    const raceCards = document.querySelectorAll('.race-card');
    raceCards.forEach(card => {
        const viewDetailsButton = card.querySelector('.view-details-button');
        if (viewDetailsButton) {
            viewDetailsButton.addEventListener('click', () => {
                const raceId = viewDetailsButton.dataset.raceId;
                window.location.href = `/race-details.html?id=${raceId}`;
            });
        }
    });
    
    // Ajouter des événements de clic aux boutons "S'inscrire"
    const registerButtons = document.querySelectorAll('.register-button');
    registerButtons.forEach(button => {
        button.addEventListener('click', function(e) {
            e.stopPropagation();
            
            // Vérifier si l'utilisateur est connecté
            if (localStorage.getItem('isLoggedIn') !== 'true') {
                showAlert('Veuillez vous connecter pour vous inscrire à une course', 'error');
                window.location.href = 'login.html';
                return;
            }
            
            const raceId = this.getAttribute('data-race-id');
            const raceName = this.getAttribute('data-race-name');
            
            // Simuler l'inscription à une course
            showAlert(`Vous êtes maintenant inscrit à ${raceName} !`, 'success');
            
            // Ajouter la course à la liste des courses de l'utilisateur
            const userRaces = JSON.parse(localStorage.getItem('userRaces') || '[]');
            if (!userRaces.includes(raceId)) {
                userRaces.push(raceId);
                localStorage.setItem('userRaces', JSON.stringify(userRaces));
            }
        });
    });
}

// Fonction pour initialiser les filtres
function initFilters() {
    const filterButtons = document.querySelectorAll('.filter-button');
    const raceCards = document.querySelectorAll('.race-card');
    
    filterButtons.forEach(button => {
        button.addEventListener('click', () => {
            const category = button.dataset.category;
            
            // Mise à jour des boutons actifs
            filterButtons.forEach(btn => btn.classList.remove('active'));
            button.classList.add('active');
            
            // Filtrage des cartes
            raceCards.forEach(card => {
                if (category === 'all' || card.dataset.category === category) {
                    card.style.display = 'block';
                } else {
                    card.style.display = 'none';
                }
            });
        });
    });
}

// Fonction pour filtrer les éléments
function filterElements(filter) {
    const elements = document.querySelectorAll('.filterable');
    
    if (filter === 'all') {
        elements.forEach(element => {
            element.classList.remove('hidden');
        });
    } else {
        elements.forEach(element => {
            if (element.getAttribute('data-category') === filter) {
                element.classList.remove('hidden');
            } else {
                element.classList.add('hidden');
            }
        });
    }
}

// Fonction pour filtrer les courses
function filterCourses(query) {
    const courses = document.querySelectorAll('.course-card');
    
    courses.forEach(course => {
        const title = course.querySelector('.course-title').textContent.toLowerCase();
        const description = course.querySelector('.course-description').textContent.toLowerCase();
        
        if (title.includes(query.toLowerCase()) || description.includes(query.toLowerCase())) {
            course.classList.remove('hidden');
        } else {
            course.classList.add('hidden');
        }
    });
}

// Fonction pour initialiser les notifications
function initNotifications() {
    const notificationContainer = document.createElement('div');
    notificationContainer.className = 'notification-container';
    document.body.appendChild(notificationContainer);
}

function showNotification(message, type = 'info') {
    const notification = document.createElement('div');
    notification.className = `alert alert-${type} fade-in`;
    notification.textContent = message;
    
    const container = document.querySelector('.notification-container');
    container.appendChild(notification);
    
    setTimeout(() => {
        notification.remove();
    }, 3000);
}

// Fonction pour afficher une alerte
function showAlert(message, type) {
    // Créer l'élément d'alerte
    const alertElement = document.createElement('div');
    alertElement.className = `fixed top-4 right-4 p-4 rounded-md shadow-md z-50 ${
        type === 'success' ? 'bg-green-500 text-white' : 'bg-red-500 text-white'
    }`;
    alertElement.textContent = message;
    
    // Ajouter l'alerte au document
    document.body.appendChild(alertElement);
    
    // Supprimer l'alerte après 3 secondes
    setTimeout(() => {
        alertElement.remove();
    }, 3000);
}

// Fonction pour gérer la déconnexion
function logout() {
    // Supprimer l'état de connexion de localStorage
    localStorage.removeItem('isLoggedIn');
    localStorage.removeItem('userName');
    
    // Rediriger vers la page d'accueil
    window.location.href = 'index.html';
}

// Ajouter un gestionnaire d'événements pour le bouton de déconnexion
document.addEventListener('DOMContentLoaded', function() {
    const logoutButton = document.querySelector('.logout-button');
    if (logoutButton) {
        logoutButton.addEventListener('click', function(e) {
            e.preventDefault();
            logout();
        });
    }
});

// Gestion du menu utilisateur
function initUserMenu() {
    const userButton = document.querySelector('.user-menu-button');
    const userMenu = document.querySelector('.user-menu');
    
    if (userButton && userMenu) {
        userButton.addEventListener('click', () => {
            userMenu.classList.toggle('hidden');
        });
        
        // Fermer le menu en cliquant en dehors
        document.addEventListener('click', (e) => {
            if (!userButton.contains(e.target) && !userMenu.contains(e.target)) {
                userMenu.classList.add('hidden');
            }
        });
    }
}

// Gestion des modales
function showModal(modalId) {
    const modal = document.getElementById(modalId);
    if (modal) {
        modal.classList.remove('hidden');
        document.body.style.overflow = 'hidden';
    }
}

function hideModal(modalId) {
    const modal = document.getElementById(modalId);
    if (modal) {
        modal.classList.add('hidden');
        document.body.style.overflow = '';
    }
}

// Gestion des formulaires de recherche
function initSearchForms() {
    const searchForms = document.querySelectorAll('.search-form');
    searchForms.forEach(form => {
        form.addEventListener('submit', (e) => {
            e.preventDefault();
            const query = form.querySelector('input[type="search"]').value;
            window.location.href = `/search.html?q=${encodeURIComponent(query)}`;
        });
    });
}

// Gestion des favoris
function toggleFavorite(raceId) {
    const user = JSON.parse(localStorage.getItem('user'));
    if (!user) {
        showNotification('Veuillez vous connecter pour ajouter des favoris', 'error');
        return;
    }
    
    let favorites = JSON.parse(localStorage.getItem('favorites') || '[]');
    const index = favorites.indexOf(raceId);
    
    if (index === -1) {
        favorites.push(raceId);
        showNotification('Course ajoutée aux favoris', 'success');
    } else {
        favorites.splice(index, 1);
        showNotification('Course retirée des favoris', 'info');
    }
    
    localStorage.setItem('favorites', JSON.stringify(favorites));
    updateFavoriteButtons();
}

function updateFavoriteButtons() {
    const user = JSON.parse(localStorage.getItem('user'));
    if (!user) return;
    
    const favorites = JSON.parse(localStorage.getItem('favorites') || '[]');
    const favoriteButtons = document.querySelectorAll('.favorite-button');
    
    favoriteButtons.forEach(button => {
        const raceId = button.dataset.raceId;
        if (favorites.includes(raceId)) {
            button.classList.add('active');
            button.innerHTML = '<i class="fas fa-heart"></i>';
        } else {
            button.classList.remove('active');
            button.innerHTML = '<i class="far fa-heart"></i>';
        }
    });
}

// Gestion des inscriptions aux courses
function registerForRace(raceId) {
    const user = JSON.parse(localStorage.getItem('user'));
    if (!user) {
        showNotification('Veuillez vous connecter pour vous inscrire à une course', 'error');
        return;
    }
    
    let registrations = JSON.parse(localStorage.getItem('registrations') || '[]');
    if (registrations.includes(raceId)) {
        showNotification('Vous êtes déjà inscrit à cette course', 'error');
        return;
    }
    
    registrations.push(raceId);
    localStorage.setItem('registrations', JSON.stringify(registrations));
    showNotification('Inscription réussie !', 'success');
    updateRegistrationButtons();
}

function updateRegistrationButtons() {
    const user = JSON.parse(localStorage.getItem('user'));
    if (!user) return;
    
    const registrations = JSON.parse(localStorage.getItem('registrations') || '[]');
    const registerButtons = document.querySelectorAll('.register-button');
    
    registerButtons.forEach(button => {
        const raceId = button.dataset.raceId;
        if (registrations.includes(raceId)) {
            button.disabled = true;
            button.textContent = 'Inscrit';
        }
    });
}

// Initialisation des composants au chargement de la page
document.addEventListener('DOMContentLoaded', () => {
    initUserMenu();
    initSearchForms();
    updateFavoriteButtons();
    updateRegistrationButtons();
});