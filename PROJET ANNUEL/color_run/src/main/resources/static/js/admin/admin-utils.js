// Gestion des images
class ImageHandler {
    constructor(options) {
        this.input = options.inputElement;
        this.preview = options.previewElement;
        this.dropZone = options.dropZone;
        
        this.initializeEventListeners();
    }
    
    initializeEventListeners() {
        // Gérer le changement de fichier
        this.input.addEventListener('change', (e) => this.handleFileSelect(e));
        
        // Gérer le drag & drop
        if (this.dropZone) {
            this.dropZone.addEventListener('dragover', (e) => {
                e.preventDefault();
                e.stopPropagation();
                this.dropZone.classList.add('border-purple-500');
            });
            
            this.dropZone.addEventListener('dragleave', () => {
                this.dropZone.classList.remove('border-purple-500');
            });
            
            this.dropZone.addEventListener('drop', (e) => {
                e.preventDefault();
                e.stopPropagation();
                this.dropZone.classList.remove('border-purple-500');
                
                const files = e.dataTransfer.files;
                if (files.length > 0) {
                    this.input.files = files;
                    this.handleFileSelect({ target: this.input });
                }
            });
        }
    }
    
    handleFileSelect(event) {
        const file = event.target.files[0];
        if (file && this.preview) {
            // Vérifier si c'est une image
            if (!file.type.startsWith('image/')) {
                showAlert('Veuillez sélectionner une image.', 'error');
                return;
            }
            
            // Vérifier la taille du fichier (5MB max)
            if (file.size > 5 * 1024 * 1024) {
                showAlert('L\'image ne doit pas dépasser 5MB.', 'error');
                return;
            }
            
            // Afficher l'aperçu
            const reader = new FileReader();
            reader.onload = (e) => {
                this.preview.src = e.target.result;
                this.preview.classList.remove('hidden');
            };
            reader.onerror = () => {
                showAlert('Erreur lors du chargement de l\'image.', 'error');
            };
            reader.readAsDataURL(file);
        }
    }
    
    static async fileToBase64(file) {
        return new Promise((resolve, reject) => {
            const reader = new FileReader();
            reader.onload = () => resolve(reader.result);
            reader.onerror = reject;
            reader.readAsDataURL(file);
        });
    }
}

// Gestion de la suppression
function initDeleteButtons() {
    // Supprimer les anciens event listeners
    const oldButtons = document.querySelectorAll('.delete-button');
    oldButtons.forEach(button => {
        button.removeEventListener('click', handleDelete);
    });

    // Ajouter les nouveaux event listeners
    const deleteButtons = document.querySelectorAll('.delete-button');
    deleteButtons.forEach(button => {
        button.addEventListener('click', handleDelete);
    });
}

async function handleDelete(e) {
    e.preventDefault();
    e.stopPropagation();
    
    const button = e.currentTarget;
    const itemType = button.dataset.type;
    const itemId = parseInt(button.dataset.id);
    
    if (!itemType || !itemId) {
        showAlert('Erreur: données de suppression invalides', 'error');
        return;
    }
    
    // Récupérer le nom de l'élément à supprimer
    let itemName = '';
    let itemTypeText = '';
    
    try {
        if (itemType === 'user') {
            const users = JSON.parse(localStorage.getItem('users') || '[]');
            const user = users.find(u => u.id === itemId);
            itemName = user ? user.name : '';
            itemTypeText = "l'utilisateur";
        } else if (itemType === 'race') {
            const races = JSON.parse(localStorage.getItem('races') || '[]');
            const race = races.find(r => r.id === itemId);
            itemName = race ? race.title : '';
            itemTypeText = 'la course';
        } else if (itemType === 'organizer') {
            const users = JSON.parse(localStorage.getItem('users') || '[]');
            const organizer = users.find(u => u.id === itemId && u.role === 'organizer');
            itemName = organizer ? organizer.name : '';
            itemTypeText = "l'organisateur";
        }
        
        if (!itemName) {
            showAlert(`${itemTypeText} non trouvé`, 'error');
            return;
        }
        
        if (confirm(`Êtes-vous sûr de vouloir supprimer ${itemTypeText} "${itemName}" ?`)) {
            if (itemType === 'race') {
                const races = JSON.parse(localStorage.getItem('races') || '[]');
                const updatedRaces = races.filter(r => r.id !== itemId);
                localStorage.setItem('races', JSON.stringify(updatedRaces));
            } else if (itemType === 'user' || itemType === 'organizer') {
                const users = JSON.parse(localStorage.getItem('users') || '[]');
                const updatedUsers = users.filter(u => u.id !== itemId);
                localStorage.setItem('users', JSON.stringify(updatedUsers));
            }
            
            showAlert(`${itemTypeText} "${itemName}" a été supprimé avec succès !`, 'success');
            
            // Recharger la page après un court délai
            setTimeout(() => {
                window.location.reload();
            }, 1500);
        }
    } catch (error) {
        console.error('Erreur lors de la suppression:', error);
        showAlert('Une erreur est survenue lors de la suppression.', 'error');
    }
}

// Fonction pour afficher une alerte
function showAlert(message, type = 'success') {
    const alertElement = document.createElement('div');
    alertElement.className = `fixed top-4 right-4 p-4 rounded-md shadow-md z-50 ${
        type === 'success' ? 'bg-green-500' : 'bg-red-500'
    } text-white`;
    alertElement.textContent = message;
    
    document.body.appendChild(alertElement);
    
    setTimeout(() => {
        alertElement.remove();
    }, 3000);
}

// Initialisation au chargement de la page
document.addEventListener('DOMContentLoaded', () => {
    initDeleteButtons();
}); 