// Initialisation des formulaires d'administration
function initAdminForms() {
    // Initialiser les gestionnaires d'images pour tous les formulaires
    initImageHandlers();
    
    // Formulaire d'ajout de course
    const addRaceForm = document.querySelector('#add-race-form');
    if (addRaceForm) {
        addRaceForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            
            const raceData = {
                title: addRaceForm.querySelector('input[name="title"]').value,
                description: addRaceForm.querySelector('textarea[name="description"]').value,
                date: addRaceForm.querySelector('input[name="date"]').value,
                location: addRaceForm.querySelector('input[name="location"]').value,
                distance: addRaceForm.querySelector('input[name="distance"]').value,
                price: addRaceForm.querySelector('input[name="price"]').value,
                capacity: addRaceForm.querySelector('input[name="capacity"]').value,
                category: addRaceForm.querySelector('select[name="category"]').value,
                image: null
            };
            
            // Gérer l'upload de l'image
            const imageInput = addRaceForm.querySelector('input[name="image"]');
            if (imageInput && imageInput.files.length > 0) {
                try {
                    raceData.image = await ImageHandler.fileToBase64(imageInput.files[0]);
                } catch (error) {
                    console.error('Erreur lors de la conversion de l\'image:', error);
                }
            }
            
            // Simuler l'ajout d'une course
            const races = JSON.parse(localStorage.getItem('races') || '[]');
            const newRaceId = races.length > 0 ? Math.max(...races.map(r => r.id)) + 1 : 1;
            raceData.id = newRaceId;
            raceData.createdAt = new Date().toISOString();
            raceData.organizerId = JSON.parse(localStorage.getItem('user')).id;
            
            races.push(raceData);
            localStorage.setItem('races', JSON.stringify(races));
            
            showAlert('Course ajoutée avec succès !', 'success');
            addRaceForm.reset();
            
            // Rediriger vers la page des courses avec une transition fluide
            setTimeout(() => {
                window.pageTransitions.navigateTo('admin-races.html');
            }, 1500);
        });
    }
    
    // Formulaire de modification de course
    const editRaceForm = document.querySelector('#edit-race-form');
    if (editRaceForm) {
        // Récupérer l'ID de la course depuis l'URL
        const urlParams = new URLSearchParams(window.location.search);
        const raceId = parseInt(urlParams.get('id'));
        
        if (raceId) {
            // Charger les données de la course
            const races = JSON.parse(localStorage.getItem('races') || '[]');
            const race = races.find(r => r.id === raceId);
            
            if (race) {
                // Remplir le formulaire avec les données de la course
                editRaceForm.querySelector('input[name="title"]').value = race.title;
                editRaceForm.querySelector('textarea[name="description"]').value = race.description;
                editRaceForm.querySelector('input[name="date"]').value = race.date;
                editRaceForm.querySelector('input[name="location"]').value = race.location;
                editRaceForm.querySelector('input[name="distance"]').value = race.distance;
                editRaceForm.querySelector('input[name="price"]').value = race.price;
                editRaceForm.querySelector('input[name="capacity"]').value = race.capacity;
                editRaceForm.querySelector('select[name="category"]').value = race.category;
                
                // Afficher l'image existante si elle existe
                if (race.image) {
                    const previewElement = editRaceForm.querySelector('#image-preview');
                    if (previewElement) {
                        previewElement.src = race.image;
                    }
                }
                
                // Ajouter l'ID de la course au formulaire
                const raceIdInput = document.createElement('input');
                raceIdInput.type = 'hidden';
                raceIdInput.name = 'raceId';
                raceIdInput.value = raceId;
                editRaceForm.appendChild(raceIdInput);
            }
        }
        
        editRaceForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            
            const raceId = parseInt(editRaceForm.querySelector('input[name="raceId"]').value);
            
            const raceData = {
                title: editRaceForm.querySelector('input[name="title"]').value,
                description: editRaceForm.querySelector('textarea[name="description"]').value,
                date: editRaceForm.querySelector('input[name="date"]').value,
                location: editRaceForm.querySelector('input[name="location"]').value,
                distance: editRaceForm.querySelector('input[name="distance"]').value,
                price: editRaceForm.querySelector('input[name="price"]').value,
                capacity: editRaceForm.querySelector('input[name="capacity"]').value,
                category: editRaceForm.querySelector('select[name="category"]').value,
                image: null
            };
            
            // Gérer l'upload de la nouvelle image
            const imageInput = editRaceForm.querySelector('input[name="image"]');
            if (imageInput && imageInput.files.length > 0) {
                try {
                    raceData.image = await ImageHandler.fileToBase64(imageInput.files[0]);
                } catch (error) {
                    console.error('Erreur lors de la conversion de l\'image:', error);
                }
            }
            
            // Mettre à jour la course
            const races = JSON.parse(localStorage.getItem('races') || '[]');
            const raceIndex = races.findIndex(r => r.id === raceId);
            
            if (raceIndex !== -1) {
                // Conserver l'ID et la date de création
                raceData.id = races[raceIndex].id;
                raceData.createdAt = races[raceIndex].createdAt;
                raceData.organizerId = races[raceIndex].organizerId;
                
                // Conserver l'image existante si aucune nouvelle image n'est uploadée
                if (!raceData.image && races[raceIndex].image) {
                    raceData.image = races[raceIndex].image;
                }
                
                races[raceIndex] = raceData;
                localStorage.setItem('races', JSON.stringify(races));
                
                showAlert('Course mise à jour avec succès !', 'success');
                
                // Rediriger vers la page des courses avec une transition fluide
                setTimeout(() => {
                    window.pageTransitions.navigateTo('admin-races.html');
                }, 1500);
            }
        });
    }
    
    // Formulaire d'ajout d'utilisateur
    const addUserForm = document.querySelector('#add-user-form');
    if (addUserForm) {
        addUserForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            
            const userData = {
                name: addUserForm.querySelector('input[name="name"]').value,
                email: addUserForm.querySelector('input[name="email"]').value,
                password: addUserForm.querySelector('input[name="password"]').value,
                role: addUserForm.querySelector('select[name="role"]').value,
                status: addUserForm.querySelector('select[name="status"]').value,
                avatar: null
            };
            
            // Gérer l'upload de l'avatar
            const avatarInput = addUserForm.querySelector('input[name="avatar"]');
            if (avatarInput && avatarInput.files.length > 0) {
                try {
                    userData.avatar = await ImageHandler.fileToBase64(avatarInput.files[0]);
                } catch (error) {
                    console.error('Erreur lors de la conversion de l\'avatar:', error);
                }
            }
            
            // Simuler l'ajout d'un utilisateur
            const users = JSON.parse(localStorage.getItem('users') || '[]');
            const newUserId = users.length > 0 ? Math.max(...users.map(u => u.id)) + 1 : 1;
            userData.id = newUserId;
            userData.createdAt = new Date().toISOString();
            
            users.push(userData);
            localStorage.setItem('users', JSON.stringify(users));
            
            showAlert('Utilisateur ajouté avec succès !', 'success');
            addUserForm.reset();
            
            // Rediriger vers la page des utilisateurs avec une transition fluide
            setTimeout(() => {
                window.pageTransitions.navigateTo('admin-users.html');
            }, 1500);
        });
    }
    
    // Formulaire de modification d'utilisateur
    const editUserForm = document.querySelector('#edit-user-form');
    if (editUserForm) {
        // Récupérer l'ID de l'utilisateur depuis l'URL
        const urlParams = new URLSearchParams(window.location.search);
        const userId = parseInt(urlParams.get('id'));
        
        if (userId) {
            // Charger les données de l'utilisateur
            const users = JSON.parse(localStorage.getItem('users') || '[]');
            const user = users.find(u => u.id === userId);
            
            if (user) {
                // Remplir le formulaire avec les données de l'utilisateur
                editUserForm.querySelector('input[name="name"]').value = user.name;
                editUserForm.querySelector('input[name="email"]').value = user.email;
                editUserForm.querySelector('select[name="role"]').value = user.role;
                editUserForm.querySelector('select[name="status"]').value = user.status;
                
                // Afficher l'avatar existant si il existe
                if (user.avatar) {
                    const previewElement = editUserForm.querySelector('#avatar-preview');
                    if (previewElement) {
                        previewElement.src = user.avatar;
                    }
                }
                
                // Ajouter l'ID de l'utilisateur au formulaire
                const userIdInput = document.createElement('input');
                userIdInput.type = 'hidden';
                userIdInput.name = 'userId';
                userIdInput.value = userId;
                editUserForm.appendChild(userIdInput);
            }
        }
        
        editUserForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            
            const userId = parseInt(editUserForm.querySelector('input[name="userId"]').value);
            
            const userData = {
                name: editUserForm.querySelector('input[name="name"]').value,
                email: editUserForm.querySelector('input[name="email"]').value,
                role: editUserForm.querySelector('select[name="role"]').value,
                status: editUserForm.querySelector('select[name="status"]').value,
                avatar: null
            };
            
            // Gérer l'upload du nouvel avatar
            const avatarInput = editUserForm.querySelector('input[name="avatar"]');
            if (avatarInput && avatarInput.files.length > 0) {
                try {
                    userData.avatar = await ImageHandler.fileToBase64(avatarInput.files[0]);
                } catch (error) {
                    console.error('Erreur lors de la conversion de l\'avatar:', error);
                }
            }
            
            // Mettre à jour l'utilisateur
            const users = JSON.parse(localStorage.getItem('users') || '[]');
            const userIndex = users.findIndex(u => u.id === userId);
            
            if (userIndex !== -1) {
                // Conserver l'ID, le mot de passe et la date de création
                userData.id = users[userIndex].id;
                userData.password = users[userIndex].password;
                userData.createdAt = users[userIndex].createdAt;
                
                // Mettre à jour le mot de passe si un nouveau est fourni
                const newPassword = editUserForm.querySelector('input[name="password"]').value;
                if (newPassword) {
                    userData.password = newPassword;
                }
                
                // Conserver l'avatar existant si aucun nouvel avatar n'est uploadé
                if (!userData.avatar && users[userIndex].avatar) {
                    userData.avatar = users[userIndex].avatar;
                }
                
                users[userIndex] = userData;
                localStorage.setItem('users', JSON.stringify(users));
                
                showAlert('Utilisateur mis à jour avec succès !', 'success');
                
                // Rediriger vers la page des utilisateurs avec une transition fluide
                setTimeout(() => {
                    window.pageTransitions.navigateTo('admin-users.html');
                }, 1500);
            }
        });
    }
    
    // Formulaire d'ajout d'organisateur
    const addOrganizerForm = document.querySelector('#add-organizer-form');
    if (addOrganizerForm) {
        addOrganizerForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            
            const organizerData = {
                name: addOrganizerForm.querySelector('input[name="name"]').value,
                email: addOrganizerForm.querySelector('input[name="email"]').value,
                password: addOrganizerForm.querySelector('input[name="password"]').value,
                company: addOrganizerForm.querySelector('input[name="company"]').value,
                phone: addOrganizerForm.querySelector('input[name="phone"]').value,
                address: addOrganizerForm.querySelector('textarea[name="address"]').value,
                role: 'organizer',
                createdAt: new Date().toISOString(),
                avatar: null
            };
            
            // Gérer l'upload de l'avatar
            const avatarInput = addOrganizerForm.querySelector('input[name="avatar"]');
            if (avatarInput && avatarInput.files.length > 0) {
                try {
                    organizerData.avatar = await ImageHandler.fileToBase64(avatarInput.files[0]);
                } catch (error) {
                    console.error('Erreur lors de la conversion de l\'avatar:', error);
                }
            }
            
            // Simuler l'ajout d'un organisateur
            const users = JSON.parse(localStorage.getItem('users') || '[]');
            const newUserId = users.length > 0 ? Math.max(...users.map(u => u.id)) + 1 : 1;
            organizerData.id = newUserId;
            
            users.push(organizerData);
            localStorage.setItem('users', JSON.stringify(users));
            
            showAlert('Organisateur ajouté avec succès !', 'success');
            addOrganizerForm.reset();
            
            // Rediriger vers la page des organisateurs avec une transition fluide
            setTimeout(() => {
                window.pageTransitions.navigateTo('admin-organizers.html');
            }, 1500);
        });
    }
    
    // Formulaire de modification d'organisateur
    const editOrganizerForm = document.querySelector('#edit-organizer-form');
    if (editOrganizerForm) {
        // Récupérer l'ID de l'organisateur depuis l'URL
        const urlParams = new URLSearchParams(window.location.search);
        const organizerId = urlParams.get('id');
        
        if (organizerId) {
            // Charger les données de l'organisateur
            const users = JSON.parse(localStorage.getItem('users') || '[]');
            const organizer = users.find(u => u.id.toString() === organizerId && u.role === 'organizer');
            
            if (organizer) {
                // Remplir le formulaire avec les données de l'organisateur
                editOrganizerForm.querySelector('input[name="name"]').value = organizer.name || '';
                editOrganizerForm.querySelector('input[name="email"]').value = organizer.email || '';
                editOrganizerForm.querySelector('input[name="company"]').value = organizer.company || '';
                editOrganizerForm.querySelector('input[name="phone"]').value = organizer.phone || '';
                editOrganizerForm.querySelector('textarea[name="address"]').value = organizer.address || '';
                editOrganizerForm.querySelector('textarea[name="description"]').value = organizer.description || '';
                editOrganizerForm.querySelector('select[name="status"]').value = organizer.status || 'pending';
                
                // Afficher l'avatar existant si il existe
                if (organizer.avatar) {
                    const previewElement = editOrganizerForm.querySelector('#avatar-preview');
                    if (previewElement) {
                        previewElement.src = organizer.avatar;
                        previewElement.classList.remove('hidden');
                    }
                }
                
                // Ajouter l'ID de l'organisateur au formulaire
                const organizerIdInput = document.createElement('input');
                organizerIdInput.type = 'hidden';
                organizerIdInput.name = 'organizerId';
                organizerIdInput.value = organizerId;
                editOrganizerForm.appendChild(organizerIdInput);
            }
        }
        
        editOrganizerForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            
            const organizerId = editOrganizerForm.querySelector('input[name="organizerId"]').value;
            
            const organizerData = {
                name: editOrganizerForm.querySelector('input[name="name"]').value,
                email: editOrganizerForm.querySelector('input[name="email"]').value,
                company: editOrganizerForm.querySelector('input[name="company"]').value,
                phone: editOrganizerForm.querySelector('input[name="phone"]').value,
                address: editOrganizerForm.querySelector('textarea[name="address"]').value,
                description: editOrganizerForm.querySelector('textarea[name="description"]').value,
                status: editOrganizerForm.querySelector('select[name="status"]').value,
                role: 'organizer',
                avatar: null
            };
            
            // Gérer l'upload du nouvel avatar
            const avatarInput = editOrganizerForm.querySelector('input[name="avatar"]');
            if (avatarInput && avatarInput.files.length > 0) {
                try {
                    organizerData.avatar = await ImageHandler.fileToBase64(avatarInput.files[0]);
                } catch (error) {
                    console.error('Erreur lors de la conversion de l\'avatar:', error);
                }
            }
            
            // Mettre à jour l'organisateur
            const users = JSON.parse(localStorage.getItem('users') || '[]');
            const organizerIndex = users.findIndex(u => u.id.toString() === organizerId && u.role === 'organizer');
            
            if (organizerIndex !== -1) {
                // Conserver l'ID, le mot de passe et la date de création
                organizerData.id = users[organizerIndex].id;
                organizerData.password = users[organizerIndex].password;
                organizerData.createdAt = users[organizerIndex].createdAt;
                
                // Mettre à jour le mot de passe si un nouveau est fourni
                const newPassword = editOrganizerForm.querySelector('input[name="password"]').value;
                if (newPassword) {
                    organizerData.password = newPassword;
                }
                
                // Conserver l'avatar existant si aucun nouvel avatar n'est uploadé
                if (!organizerData.avatar && users[organizerIndex].avatar) {
                    organizerData.avatar = users[organizerIndex].avatar;
                }
                
                users[organizerIndex] = organizerData;
                localStorage.setItem('users', JSON.stringify(users));
                
                showAlert('Organisateur mis à jour avec succès !', 'success');
                
                // Rediriger vers la page des organisateurs avec une transition fluide
                setTimeout(() => {
                    window.pageTransitions.navigateTo('admin-organizers.html');
                }, 1500);
            }
        });
    }
}

// Initialiser les gestionnaires d'images
function initImageHandlers() {
    // Gestionnaire d'images pour les formulaires de course
    document.querySelectorAll('input[type="file"][name="image"]').forEach(input => {
        const previewElement = input.closest('form').querySelector('#image-preview');
        const dropZone = input.closest('.border-dashed');
        
        new ImageHandler({
            inputElement: input,
            previewElement: previewElement,
            dropZone: dropZone
        });
    });
    
    // Gestionnaire d'images pour les formulaires d'utilisateur
    document.querySelectorAll('input[type="file"][name="avatar"]').forEach(input => {
        const previewElement = input.closest('form').querySelector('#avatar-preview');
        const dropZone = input.closest('.border-dashed');
        
        new ImageHandler({
            inputElement: input,
            previewElement: previewElement,
            dropZone: dropZone
        });
    });
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

// Fonction pour initialiser les boutons de suppression
function initDeleteButtons() {
    const deleteButtons = document.querySelectorAll('.delete-button');
    deleteButtons.forEach(button => {
        // Supprimer l'ancien écouteur d'événements s'il existe
        button.removeEventListener('click', handleDelete);
        // Ajouter le nouvel écouteur d'événements
        button.addEventListener('click', handleDelete);
    });
}

// Fonction de gestion de la suppression
function handleDelete(e) {
    e.preventDefault();
    
    const button = e.currentTarget;
    const itemType = button.dataset.type;
    const itemId = parseInt(button.dataset.id);
    
    // Récupérer le nom de l'élément à supprimer
    let itemName = '';
    if (itemType === 'user') {
        const users = JSON.parse(localStorage.getItem('users') || '[]');
        const user = users.find(u => u.id === itemId);
        itemName = user ? user.name : '';
    } else if (itemType === 'race') {
        const races = JSON.parse(localStorage.getItem('races') || '[]');
        const race = races.find(r => r.id === itemId);
        itemName = race ? race.title : '';
    }
    
    if (confirm(`Êtes-vous sûr de vouloir supprimer ${itemType === 'user' ? 'l\'utilisateur' : 'la course'} "${itemName}" ?`)) {
        if (itemType === 'race') {
            // Supprimer une course
            const races = JSON.parse(localStorage.getItem('races') || '[]');
            const updatedRaces = races.filter(r => r.id !== itemId);
            localStorage.setItem('races', JSON.stringify(updatedRaces));
        } else if (itemType === 'user') {
            // Supprimer un utilisateur
            const users = JSON.parse(localStorage.getItem('users') || '[]');
            const updatedUsers = users.filter(u => u.id !== itemId);
            localStorage.setItem('users', JSON.stringify(updatedUsers));
        }
        
        showAlert('Élément supprimé avec succès !', 'success');
        
        // Recharger la page
        setTimeout(() => {
            window.location.reload();
        }, 1500);
    }
}

// Fonction pour filtrer les utilisateurs
function filterUsers(filter) {
    const users = JSON.parse(localStorage.getItem('users') || '[]');
    const tableBody = document.getElementById('users-table-body');
    
    // ... existing filtering code ...
    
    tableBody.innerHTML = tableContent;
    
    // Initialiser les boutons de suppression après la mise à jour du tableau
    initDeleteButtons();
}

// Initialisation des formulaires d'administration au chargement de la page
document.addEventListener('DOMContentLoaded', () => {
    initAdminForms();
    // Initialiser les boutons de suppression au chargement de la page
    initDeleteButtons();
}); 