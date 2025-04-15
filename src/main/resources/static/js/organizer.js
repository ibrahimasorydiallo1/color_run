// Gestion de la navigation mobile
document.addEventListener('DOMContentLoaded', function() {
    // Toggle mobile menu
    const mobileMenuButton = document.querySelector('.mobile-menu-button');
    const mobileMenu = document.querySelector('.mobile-menu');
    
    if (mobileMenuButton && mobileMenu) {
        mobileMenuButton.addEventListener('click', function() {
            mobileMenu.classList.toggle('hidden');
            const isExpanded = mobileMenuButton.getAttribute('aria-expanded') === 'true';
            mobileMenuButton.setAttribute('aria-expanded', !isExpanded);
        });
    }

    // Toggle user menu
    const userMenuButton = document.querySelector('.user-menu-button');
    const userMenu = document.querySelector('.user-menu');
    
    if (userMenuButton && userMenu) {
        userMenuButton.addEventListener('click', function() {
            userMenu.classList.toggle('hidden');
        });

        // Close user menu when clicking outside
        document.addEventListener('click', function(event) {
            if (!userMenuButton.contains(event.target) && !userMenu.contains(event.target)) {
                userMenu.classList.add('hidden');
            }
        });
    }

    // Gestion des formulaires
    initForms();
    
    // Gestion des paiements
    initPaymentManagement();
    
    // Gestion des photos de profil
    initProfilePhotoUpload();
});

// Initialisation des formulaires
function initForms() {
    // Formulaire d'édition de course
    const editRaceForm = document.getElementById('edit-race-form');
    if (editRaceForm) {
        editRaceForm.addEventListener('submit', saveRaceChanges);
        
        // Boutons d'ajout de catégories et d'événements
        const addCategoryButton = document.querySelector('.add-category-button');
        const addEventButton = document.querySelector('.add-event-button');
        
        if (addCategoryButton) {
            addCategoryButton.addEventListener('click', addNewCategory);
        }
        
        if (addEventButton) {
            addEventButton.addEventListener('click', addNewEvent);
        }
    }

    // Formulaire de profil
    const profileForm = document.getElementById('profile-form');
    if (profileForm) {
        profileForm.addEventListener('submit', saveProfileChanges);
        initProfilePhotoUpload();
    }

    // Formulaire de paramètres
    const settingsForm = document.getElementById('settings-form');
    if (settingsForm) {
        settingsForm.addEventListener('submit', saveSettings);
    }
}

// Gestion des courses
function saveRaceChanges(event) {
    event.preventDefault();
    
    // Simuler l'envoi des données au serveur
    showNotification('Les modifications ont été enregistrées avec succès', 'success');
}

function addNewCategory() {
    const categoriesContainer = document.querySelector('.categories-container');
    if (!categoriesContainer) return;

    const categoryCount = categoriesContainer.children.length + 1;
    
    const newCategory = document.createElement('div');
    newCategory.className = 'grid grid-cols-1 md:grid-cols-3 gap-4';
    newCategory.innerHTML = `
        <div>
            <label for="category-${categoryCount}-name" class="block text-sm font-medium text-gray-700 mb-2">Nom de la catégorie</label>
            <input type="text" id="category-${categoryCount}-name" name="category-${categoryCount}-name" class="w-full px-4 py-2 border rounded-lg" required>
        </div>
        <div>
            <label for="category-${categoryCount}-price" class="block text-sm font-medium text-gray-700 mb-2">Prix</label>
            <input type="number" id="category-${categoryCount}-price" name="category-${categoryCount}-price" class="w-full px-4 py-2 border rounded-lg" required>
        </div>
        <div>
            <label for="category-${categoryCount}-capacity" class="block text-sm font-medium text-gray-700 mb-2">Capacité</label>
            <input type="number" id="category-${categoryCount}-capacity" name="category-${categoryCount}-capacity" class="w-full px-4 py-2 border rounded-lg" required>
        </div>
    `;
    
    categoriesContainer.appendChild(newCategory);
}

function addNewEvent() {
    const eventsContainer = document.querySelector('.events-container');
    if (!eventsContainer) return;

    const eventCount = eventsContainer.children.length + 1;
    
    const newEvent = document.createElement('div');
    newEvent.className = 'grid grid-cols-1 md:grid-cols-3 gap-4';
    newEvent.innerHTML = `
        <div>
            <label for="schedule-${eventCount}-time" class="block text-sm font-medium text-gray-700 mb-2">Heure</label>
            <input type="time" id="schedule-${eventCount}-time" name="schedule-${eventCount}-time" class="w-full px-4 py-2 border rounded-lg" required>
        </div>
        <div>
            <label for="schedule-${eventCount}-event" class="block text-sm font-medium text-gray-700 mb-2">Événement</label>
            <input type="text" id="schedule-${eventCount}-event" name="schedule-${eventCount}-event" class="w-full px-4 py-2 border rounded-lg" required>
        </div>
        <div>
            <label for="schedule-${eventCount}-location" class="block text-sm font-medium text-gray-700 mb-2">Lieu</label>
            <input type="text" id="schedule-${eventCount}-location" name="schedule-${eventCount}-location" class="w-full px-4 py-2 border rounded-lg" required>
        </div>
    `;
    
    eventsContainer.appendChild(newEvent);
}

// Gestion du profil
function saveProfileChanges(event) {
    event.preventDefault();
    
    // Simuler l'envoi des données au serveur
    showNotification('Les modifications du profil ont été enregistrées avec succès', 'success');
}

function initProfilePhotoUpload() {
    const profilePhotoInput = document.getElementById('profile-photo');
    const profilePhotoPreview = document.getElementById('profile-photo-preview');
    
    if (profilePhotoInput && profilePhotoPreview) {
        profilePhotoInput.addEventListener('change', function(event) {
            const file = event.target.files[0];
            if (file) {
                // Vérifier le type de fichier
                if (!file.type.match('image.*')) {
                    showNotification('Veuillez sélectionner une image valide', 'error');
                    return;
                }
                
                // Vérifier la taille du fichier (5MB max)
                if (file.size > 5 * 1024 * 1024) {
                    showNotification('L\'image ne doit pas dépasser 5MB', 'error');
                    return;
                }
                
                // Afficher l'aperçu
                const reader = new FileReader();
                reader.onload = function(e) {
                    profilePhotoPreview.src = e.target.result;
                };
                reader.readAsDataURL(file);
            }
        });
    }
}

// Gestion des paramètres
function saveSettings(event) {
    event.preventDefault();
    
    // Simuler l'envoi des données au serveur
    showNotification('Les paramètres ont été enregistrés avec succès', 'success');
}

// Gestion des participants
function viewParticipant(id) {
    // Simuler l'affichage des détails du participant
    console.log(`Voir les détails du participant ${id}`);
}

function editParticipant(id) {
    // Simuler l'édition du participant
    console.log(`Éditer le participant ${id}`);
}

function deleteParticipant(id) {
    if (confirm('Êtes-vous sûr de vouloir supprimer ce participant ?')) {
        // Simuler la suppression du participant
        console.log(`Supprimer le participant ${id}`);
        showNotification('Le participant a été supprimé avec succès', 'success');
    }
}

// Système de notification
function showNotification(message, type = 'success') {
    // Créer l'élément de notification
    const notification = document.createElement('div');
    notification.className = `fixed top-4 right-4 p-4 rounded-lg shadow-lg ${
        type === 'success' ? 'bg-green-500' : 'bg-red-500'
    } text-white`;
    notification.textContent = message;
    
    // Ajouter la notification au document
    document.body.appendChild(notification);
    
    // Supprimer la notification après 3 secondes
    setTimeout(() => {
        notification.remove();
    }, 3000);
}

// Fonction de déconnexion
function logout() {
    // Simuler la déconnexion
    window.location.href = '../../index.html';
}

// Initialisation de la gestion des paiements
function initPaymentManagement() {
    // Gestion des paiements dans la page participants
    const paymentStatusButtons = document.querySelectorAll('.payment-status-button');
    paymentStatusButtons.forEach(button => {
        button.addEventListener('click', function() {
            const participantId = this.dataset.participantId;
            const currentStatus = this.dataset.status;
            updatePaymentStatus(participantId, currentStatus);
        });
    });
    
    // Formulaire de paiement
    const paymentForm = document.getElementById('payment-form');
    if (paymentForm) {
        paymentForm.addEventListener('submit', function(e) {
            e.preventDefault();
            processPayment();
        });
    }
}

// Mise à jour du statut de paiement
function updatePaymentStatus(participantId, currentStatus) {
    // Logique pour mettre à jour le statut de paiement
    console.log(`Mise à jour du statut de paiement pour le participant ${participantId} de ${currentStatus}`);
    
    // Afficher un message de succès
    showNotification('Statut de paiement mis à jour', 'success');
}

// Traitement du paiement
function processPayment() {
    // Récupération des données du formulaire
    const formData = new FormData(document.getElementById('payment-form'));
    const paymentData = {};
    
    for (const [key, value] of formData.entries()) {
        paymentData[key] = value;
    }
    
    // Simulation d'envoi au serveur
    console.log('Données de paiement à traiter:', paymentData);
    
    // Afficher un message de succès
    showNotification('Paiement traité avec succès', 'success');
    
    // Fermer le modal de paiement
    const paymentModal = document.getElementById('payment-modal');
    if (paymentModal) {
        paymentModal.classList.add('hidden');
    }
} 